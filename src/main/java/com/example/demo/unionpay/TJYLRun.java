package com.example.demo.unionpay;

import java.util.HashMap;
import java.util.Map;

import com.example.demo.entity.DFModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TJYLRun {

	/** 天津银联商户id **/
	private static final String merchantID = "309113148161009"; // 309113148161009
																// 309113148162009
	/** 天津银联代付ID **/
	private static final String DFmerchantID = "80001";

	/** 生产环境 **/
	private static final String url = "144.112.33.225:8830";

	private static final String password = "1234";

	private static final String signCert = "/usr/local/xydf.pfx";

	private static final Integer timeOut = 180;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public boolean init(DFModel dfModel) {

		// 组包
		HashMap<String, String> allDataElement = new HashMap<String, String>();
		// 版本号
		allDataElement.put("version", "1.0");
		// 交易类型
		allDataElement.put("txnType", "12");
		// 商户代码
		allDataElement.put("merId", merchantID);
		// 清算时效标志
		allDataElement.put("settType", "1");
		// 交易子类
		allDataElement.put("signMethod", "01");
		// 订单发送时间
		allDataElement.put("txnTime", dfModel.getTxnTime());
		allDataElement.put("orderId", dfModel.getOrderNumber());
		// 收款账号   6212260200069864706
		allDataElement.put("accNo", dfModel.getAccNo());
		// 后台通知地址
		allDataElement.put("backUrl", "127.0.0.1:80");
		// 交易金额
		allDataElement.put("txnAmt", dfModel.getTxnAmt());
		// 企业编号
		allDataElement.put("enterpriseNo", DFmerchantID);
		// 收款人银行中文名称   "工商银行"
		allDataElement.put("BankName", dfModel.getBankName());
		// 收款人名称 "周芳禹"
		allDataElement.put("payName", dfModel.getPayName());
		// 公/私标识
		allDataElement.put("ppType", "0");
		// 备注
		allDataElement.put("note", "实时交易");
		allDataElement.put("certifTp" , "01");
		allDataElement.put("certifId" , "230828199110140930");

		// 如果支付返回不是00或者不是空，则认为交易失败，否则进入查询页面
		Map<String, String> retMap = TJYLBase.submitDate(allDataElement, url, signCert, password);

		if (retMap != null && !retMap.get("respCode").equals("00") && !retMap.get("respCode").equals("03")
				&& !retMap.get("respCode").equals("04") && !retMap.get("respCode").equals("Z19")) {
			
			logger.info("代付" + allDataElement.get("orderId") + "交易应答码为=【" + retMap.get("respCode") + "#" + retMap.get("respMsg") + "】,不需查询交易状态");
			
			if (retMap.get("respCode").equals("00")) {
				logger.info("代付" + allDataElement.get("orderId") + retMap.toString());

				// 代付流水号 后续查询使用
				logger.info("查询代付结果成功");
				return true;
			} else {
				logger.info("代付" + allDataElement.get("orderId") + retMap.toString());
				// 设置提现交易状态为failed
				// 设置提现交易失败结果信息
				return false;
			}
		} else {
			HashMap<String, String> queryMap = new HashMap<String, String>();
			Map<String, String> retQueryMap = new HashMap<String, String>();
			queryMap.put("version", "1.0");// 版本号
			queryMap.put("txnType", "00");// 交易类型
			queryMap.put("merId", merchantID);// 商户代码
			queryMap.put("signMethod", "01");// 交易子类
			queryMap.put("enterpriseNo", DFmerchantID);// 企业编号
			queryMap.put("orderId", allDataElement.get("orderId"));
			if (retMap != null) {
				queryMap.put("queryId", retMap.get("queryId"));
			}
			queryMap.put("txnTime", allDataElement.get("txnTime"));
			int sllep = 0;
			long l2 = 0;
			long l = System.currentTimeMillis();

			while ((l2 - l) < timeOut * 1000) {
				l2 = System.currentTimeMillis();
				retQueryMap = TJYLBase.submitDate(queryMap, url, signCert, password);
				try {
					if (retQueryMap == null) {
						// 未返回查询数据时需要重复查询
						logger.info("代付" + allDataElement.get("orderId") + "未收到查询报文,需继续查询交易状态");
						sllep = sllep + 1;
						Thread.sleep(sllep * 1000);
					} else if (retQueryMap.get("respCode").equals("00")) {
						// 查询成功且原交易状态不确认时需要重新发送交易查询
						if (retQueryMap.get("origRespCode").equals("03") || retQueryMap.get("origRespCode").equals("04")
								|| retQueryMap.get("origRespCode").equals("Z19")) {
							
							logger.info("代付" + allDataElement.get("orderId") + "原交易应答码=【" + retQueryMap.get("origRespCode") + "#" + retQueryMap.get("origRespMsg") + "】,需继续查询交易状态");
							sllep = sllep + 1;
							Thread.sleep(sllep * 1000);
						} else {
							logger.info("代付" + allDataElement.get("orderId") + "原交易应答码=【" + retQueryMap.get("origRespCode") + "#" + retQueryMap.get("origRespMsg") + "】，不需要继续查询交易状态");
							break;
						}
					} else {
						// 查询失败需要重新发送查询请求
						sllep = sllep + 1;
						Thread.sleep(sllep * 1000);
						logger.info("代付" + allDataElement.get("orderId") + "查询交易应答码=【" + retQueryMap.get("respCode") + "#" + retQueryMap.get("respMsg") + "】，需要继续查询交易状态");
					}
				} catch (InterruptedException e) {
					logger.error("查询天津银联代付请求出现异常 : " + e.getMessage());
					return false;
				}
			}
			if (retQueryMap == null) {
				logger.info("代付" + allDataElement.get("orderId") + "查询结果为空");
				return false;
			} else if (retQueryMap.get("respCode").equals("00")) {
				logger.info("代付" + allDataElement.get("orderId") + retQueryMap.get("origRespCode") + retQueryMap.get("origRespMsg"));
				// 代付流水号 后续查询使用
				logger.info("查询代付结果成功");
				return true;
			} else {
				logger.info("代付" + allDataElement.get("orderId") + retQueryMap.get("origRespCode") + retQueryMap.get("origRespMsg"));
				// 设置提现交易状态为failed
				// 设置提现交易失败结果信息
				return false;
			}
		}
	}
}

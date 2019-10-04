package tencent.wx.pay;

import artoria.exchange.XStreamXmlProvider;
import artoria.exchange.XmlUtils;
import artoria.identifier.IdentifierUtils;
import com.alibaba.fastjson.JSON;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static artoria.common.Constants.MD5;
import static java.lang.Boolean.TRUE;

@Ignore
public class WxPayServiceTest {
    private static Logger log = LoggerFactory.getLogger(WxPayServiceTest.class);
    private static String mchKey = "mchKey";
    private static String mchId = "mchId";
    private static String appId = "appId";
    private static WxPayService wxPayService = new WxPayServiceImpl(appId, mchId, mchKey);
    private static WxUnifiedOrderRequest testRequest = new WxUnifiedOrderRequest();

    static {
        XmlUtils.setXmlProvider(new XStreamXmlProvider());
        testRequest.setOutTradeNo(IdentifierUtils.nextStringIdentifier());
        testRequest.setBody("JSAPI支付测试");
        testRequest.setAttach("支付测试");
        testRequest.setTotalFee(1);
        testRequest.setOpenId("o60Jl1oW_97s");
        testRequest.setSpbillCreateIp("127.0.0.1");
        testRequest.setNotifyUrl("http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php");
        testRequest.setTradeType("JSAPI");
        testRequest.setNonceStr(IdentifierUtils.nextStringIdentifier());
//        testRequest.setSignType("MD5");
//        testRequest.setSignType("HMAC-SHA256");
    }

    @Test
    public void unifiedOrderTest() {
        WxUnifiedOrderResult result = wxPayService.unifiedOrder(testRequest);
        log.info("result = {}", JSON.toJSONString(result, TRUE));
    }

    @Test
    public void paymentInfoTest() {
        WxPaymentInfoResult result = wxPayService.paymentInfo(testRequest);
        log.info("result = {}", JSON.toJSONString(result, TRUE));
    }

    @Test
    public void parseOrderPayNotifyTest() {
        String notify = "<xml>\n" +
                "  <appid><![CDATA[wxb]]></appid>\n" +
                "  <attach><![CDATA[支付测试]]></attach>\n" +
                "  <bank_type><![CDATA[CFT]]></bank_type>\n" +
                "  <fee_type><![CDATA[CNY]]></fee_type>\n" +
                "  <is_subscribe><![CDATA[Y]]></is_subscribe>\n" +
                "  <mch_id><![CDATA[100]]></mch_id>\n" +
                "  <nonce_str><![CDATA[5d2b6e531c]]></nonce_str>\n" +
                "  <openid><![CDATA[oUpekE]]></openid>\n" +
                "  <out_trade_no><![CDATA[1453]]></out_trade_no>\n" +
                "  <result_code><![CDATA[SUCCESS]]></result_code>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <sign><![CDATA[B552E8AB241]]></sign>\n" +
                "  <time_end><![CDATA[2014131540]]></time_end>\n" +
                "  <total_fee>1</total_fee>\n" +
                "  <coupon_fee><![CDATA[10]]></coupon_fee>\n" +
                "  <coupon_count><![CDATA[1]]></coupon_count>\n" +
                "  <coupon_type><![CDATA[CASH]]></coupon_type>\n" +
                "  <coupon_id><![CDATA[10000]]></coupon_id>\n" +
                "  <coupon_fee><![CDATA[100]]></coupon_fee>\n" +
                "  <trade_type><![CDATA[JSAPI]]></trade_type>\n" +
                "  <transaction_id><![CDATA[100468]]></transaction_id>\n" +
                "</xml>";
        WxOrderPayNotifyResult result = wxPayService.parseOrderPayNotify(notify, MD5);
        log.info("result = {}", JSON.toJSONString(result, true));
    }

}

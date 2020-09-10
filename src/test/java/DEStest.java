import com.fasterxml.jackson.core.JsonProcessingException;
import iotpackage.constructor.PackageConstructor;
import iotpackage.destination.Destination;
import iotpackage.source.Source;
import org.junit.Test;
import securityalgorithm.*;
import java.util.Map;

/**
 * 前后端rsa数据传输
 */
public class DEStest {
    public static void main(String[] args) throws Exception{
        String KEY_STR="mykey";
        Map<String,String> KpairMap=RSAUtil.createKeys(2048,KEY_STR);

        /*
         * 获取公钥
         * */
        System.out.print("\n公钥\n"+KpairMap.get("publicKey"));

        /*
         * 获取私钥
         * */
        System.out.print("\n私钥\n"+KpairMap.get("privateKey"));
        String msg="这不难理解。在这个演示样例中，仅仅有一个名为 people 的变量。值是包括三个条目的数组，每一个条目是一个人的记录，当中包括名、姓和电子邮件地址。上面的演示样例演示怎样用括号将记录组合成一个值。\n" +
                "\n" +
                "当然，能够使用同样的语法表示多个值（每一个值包括多个记录）：\n" +
                "\n" +
                "{ \"programmers\": [\n" +
                "  { \"firstName\": \"Brett\", \"lastName\":\"McLaughlin\", \"email\": \"brett@newInstance.com\" },\n" +
                "  { \"firstName\": \"Jason\", \"lastName\":\"Hunter\", \"email\": \"jason@servlets.com\" },\n" +
                "  { \"firstName\": \"Elliotte\", \"lastName\":\"Harold\", \"email\": \"elharo@macfaq.com\" }\n" +
                " ],\n" +
                "\"authors\": [\n" +
                "  { \"firstName\": \"Isaac\", \"lastName\": \"Asimov\", \"genre\": \"science fiction\" },\n" +
                "  { \"firstName\": \"Tad\", \"lastName\": \"Williams\", \"genre\": \"fantasy\" },\n" +
                "  { \"firstName\": \"Frank\", \"lastName\": \"Peretti\", \"genre\": \"christian fiction\" }\n" +
                " ],\n" +
                "\"musicians\": [\n" +
                "  { \"firstName\": \"Eric\", \"lastName\": \"Clapton\", \"instrument\": \"guitar\" },\n" +
                "  { \"firstName\": \"Sergei\", \"lastName\": \"Rachmaninoff\", \"instrument\": \"piano\" }\n" +
                " ]\n" +
                "}";

        String msg2="fZ6zdyeCDywFka5smoZ-jDVrfZX0o6-xih1mN7vYilQqNGrPPcX6n0sggx8DQwaOCzM-paywkrmV5NtZNeNy5baL_WP22puBwvOGgpiyUG1OWt7vdb_tVoH48X3yIcGiCd19_XQVkl0sZ2GylVxjm5dcMBvi6TpTLyglhDGPByEymXJXeBfm0xxMqONiK0AyGeRhqII5RywdgepI9pmIox0osT2bA01LeIo7OMfd7hA_19ZO1lIyLKSsjE3SxNFX4DX2Vod_RvyUupzOOPPeJO_SGrBqRmvMFPWaBSt6sH85dAMrlLyDymWNNzrDHAcMU7NJHyglOv6T1bNbNo3Qf4DMo0DMCADFbFIyH4n8NYP17yCjaxZoLMY5bHx8AzxaisBSYZOL4aVayKMKyJgj6USOl0oWmdz9fXSP7C9UTg3Q_oaVeWXM0Iytg6ax9VyPIk4CxF7AWo0FtaZwn3SNJM5_E-ckige4Vjyfd5HmsymrjK6J9XJe-aGyN7Jqkqo4B9goRrDOt8Da-Ptkl_NT_uvmm9tqYcrBGSX-sTfiRAgDQ7PR8wKSouIoNCgEZZVYHrFJMNTN5mjg46fd3-6X99CNWBODeE2jyel5kbGam2LXOTKtm_5gJ5aVsB5QDCEEqxAd9QTkfhBrBOvVZxrgoK6gpWz_cMnr__HnemBEbEpNOPq_VTa_jKhGtaQL7CaUrwmPN-YKY0I6Q88fKfA-9lcW2utuIwjyXZ5I9ct8_1cWhyQgHl9pRYA-gk9JVlxffXqQg4oHsMJsWcrelq9LVzyKjdq2xDgMHHNF4bsTq7juis2R82f4t_AEysOJMbSSjfm39b6aSNcKwnOPkgbmLcSbYBq_JU7EjT5dlUbH5lKhIGnlFdCKn858rv8wcU33PKNjFfNoY_kzXo3ImAkQY0Bdlb24m3fFpImtIW27a-4GdPpILMcA7EFddKkma5PlpQ_LClzoolqk1qIcOw4FRGSV2UoJYB4ykA_rNvk9AKT4y6y306tb7XJ2VEp8wye_fKPrBsicMYgKh8yqjJk-TqC5WbVd1zAI4q244ABEDTdhm-y-ud3x8CQ-9ltlCGnlpDrqJPLrjKzPTIE3bSiYd1GvbAXRhvgn14n9Na9ncJJWdATcFSurPrE0v0eM6B7O9Lm2a-P4S6jFjLnFCoOFlpHSyRSN6_4oxxnbmfmNUagzFWQPQhW4fPG8pqBUiN_eMILdqd0ztCBZiD7Ow1VJHARVjh3gx3aGyynvjZhi98DXpMN19O4EYuzYgLwIyWx_4Vqx7hgopo1XGcxD_uq8UbVfATMy3pNxymq7HQyT68oGnvTxJVxa9kjHSmt2gYua5dhxvJExLs84yZj-U0Y8lkKAM92tPWthnszNQni-BizIlRi2dlFrfXCY4Blg7Tofafm-sk4nozxD-EKweY-MaGm_Ne6zThjoh5NCvWZKWVALSSRdJZ-1kuhFvIitdtT2c_sqE9eNVequDgdBrreEyZ5eK2U2Jv0txLt_9WRBhGQlH8LLsEBYHOtWRpqK2vaX5LAX_QzyfdPeT4uAbkRn9k-8nw0V4UfgVssa3rZIE8ghC_VbIcWWRj-NdFmb_v8HHmX-W5YRZUr8dRC1dA8c0mRGH7EWhO2VaiqN_PpbFSaYosMMoMETyI7fk2malZy2I2c5v1QY4dUzCGHUD2V5DRq0V5BenMOlbdJ3-irsYI0\n";
        String msg3="hqp3QxRffMPg2GxhuDkp_Bxzio_18Co52wFQHG2Az4X7m8Kh42UdSbStaekGYGv4Wz4TwZHon3Y8mzaU8xAWZe09GIo8kzCEZ3jjGbf0EAIkLNfyZrGek6coqIiNOxhhcL_TMWrbQYKA9BK8R_WOLMOpMjAL_s9Bz0Veqw-jLRYWn0ZWW0oSxOxdga9iJkhiuFJO93Cm83ekjdVyo8edT5J6c4YGsPKdOwWC2zTA2OL95R8MITIKHz-GlEHnT1gQVfwuqS_v2NG7srFmBtjszBSwKi4aZYFRug9g3vuGVdc4O3-Ep43T5kWaKGjsKyjgxws38411IqGZmaMRydoQrRnuMGYasK_LspyhMnHWdIwpxL0I0t71ob0iWMgk6o-5izyGLhoDTuult7NuF8aOthkvwXQltDosA5LyrJ6sgTMeZbnokilcaGYEpM3Y8MGEYcb8xgSneB2BinKZMp1mvES8CX7M9OmXQO7kk44idNzkTVWRXULBWxlxBsOPnAWAdJ3fJXsa1gHRFJwB_aSXl9GSNFb4SkOw-iypYmuMGtOiYp8QQygX2PgDMxQSf9lZOxtdFRLHqI4Sn89pSdiYtFYVHKXcIZdeEKVyYmvSMC0XgYkMtkobSkvl8entOCrc9uSDKD-rAovnoSJueUFTL8sj-LJ_ico9Fn9xINYPfZhxrM_fRuJs9-lmHDXLtykVkcg7P7icLvT0gR-etc8AJvJawd-ZN3GAKOlSY7QJdtYfTgHuHRSecPOHOHx20xLPVeIlmzOxoG6BvSq0qAHF77wR2MpwqOwedMydsYPZkWXZwXhl8YSVEOtxI-ZJ2BdDJdSa7R5VlY67KXz9LoEBUXWtOxWHm5WembJQyMKpx9_C855AihYx8yA8Sj0cBpeVsqwv83kgQjkTWnH_94n4QsfsJiqYgsK0g2OBdK7dWt5-c7jD_83D-tiyOznGHk0_4wqyOJ1zclXU6oLy_C1sv_oNHLD-krSTfvtJf4vPd1f6UDmCcNA-PcmPY6pqkiLWDoLknGE80jea0J7qaGtavO7cEtMrzjQTP2w5oVbiUVbvQzH4_n7qFVupx_Jg4QWBuKlsUU-Fupddx_c10SC-UbEFx4Z8e40c9eTc0EIUwt2_nC_8X7DnCzQRVT2qcWzcnyy0JuZ-un3pRLZgSP0BEvj2U2MVtLF_7qsGpIfj6SJQxka5DPpBoBM61f1cvho0PP2f2_yvBTtsguvIzmRF0je7y_z8X81BRDvnYGDDfFMpiO7MsuNX0IuKLdnodSklDG-oB2e_ccEnEMsm9BuuTLTVCksd_joV_Qsan9WR8jgwOvBRw_4XsIt9GTalRYVXW75ZPPCOikfEhO_xeoaE7El9rb71hdDMnqNdRjKjqWXdMFVkajp1y__nE1m-g2TBZE670CjyYiKE5-683ZQpK1m5fI2ZjsefgBDUfCFqLksYXQoqMM6aEtlmIt8s6w-5PosSn27oxD8fVpOmsFXZDAkK7Nipc511hSb9rhDt3kHsp6upYxJtvV_5vtjEtTfYwSQFDJyFUhbWR11iDq5tvbniX4ap8yvOHg7RzW7SZCW-TyX2N_qJMBTkkFcwdg6-Sg-ATBHF96CCT1LdgVGvSkOBHHiTU4YW_y8PsA8ZZ_PAOt4_QiZxQOcUSkZQA3Jjk0pxCUi7Bf_aFHtKq_DI9hHLChBjyfTGAlGZ-sUv5no\n";
        /*
         * 加密测试
         * */
        String encodeData=RSAUtil.publicEncrypt(msg,KpairMap.get("publicKey"));
        System.out.print("\n\n加密结果:\n"+encodeData);
        /*
         * 解密测试
         * */
        String decodeData=RSAUtil.privateDecrypt(encodeData,KpairMap.get("privateKey"));
        System.out.print("\n\n解密结果:\n"+decodeData);

        /*
         * 签名结果
         * */
        String qianming=RSAUtil.privateEncrypt(msg,KpairMap.get("privateKey"));
        System.out.print("\n\n签名结果:\n"+qianming);

        /*
         * 验证结果
         * */
        String yanzheng=RSAUtil.publicDecrypt(qianming,KpairMap.get("publicKey"));
        System.out.print("\n\n验证结果:\n"+yanzheng);
    }

    @Test
    public void testhasrsa() throws JsonProcessingException {
        String pulickey="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCR52yBgDacpdlQ4m6kaKzKEvfG" +
                "58U6JBYJNVtI11Sevqcpx5PfZAMkqIV1HoOj0LDKcuiDzBLTtFv4bbUVvtl79C-J_t2YVt-HblV-aNtBGhL2-7B4P" +
                "PZfYwcRkV5kGUhtrTP-ZWT9KOYH6k8UIFenB8Q2YNxtvuhMrKjBiLFxQcNyBPJKLd5JPpCu-NH34yqag5F6tTRITD" +
                "XT8h_HpAj6HEpglENyZoaSf1MZKvVKl7UBH7fFpQabtCJrEEqtiuB4SqwVKrDg7a_Zv2z9eC6WvIugyjNhIa6WDyR" +
                "DSmHviD-fc5ieT2dvMmb3G1fDVWM8TysUgV2FaIxKcz9ESSg_AgMBAAECggEACDJ6VCRB7TccKIEwOihvXMMIDLLE" +
                "z-lrG4VV5ZfDd0-60IwX8LsLuimFpfja6H83D4i5K35xqFc_u4teWyRnz6D-csBQ7tgiotc-EmYNu3CdlCa72WWDa" +
                "gSERyZlqTYKba8Hdsl3jQmZMuSlILpYclfrXCrA96F8J6KjsIvffD9h5a_N15Kpai1OpBh4y5o2_CHkUGoOQEGYzo" +
                "-EkUOXesVA8V698rd45SWcwXmu6ZyNpFjmeicT2q1IWiB9FP-njg7_ybPW44qVYrIZrEOaVajWU2XBxyFaxXBqa2p" +
                "yGwInazcL_2Ff5Hzx7SUbUmSSoDF3FTqd_cGkuYS4IKSlAQKBgQDEGy8TCbmudPGJXptxnBWHZyib-2mKxWVanHAT" +
                "Kpd51xkRRt5P6yyh_cwLK5tBa9PADo0BxDQxB3pyPn75yR1OlNCuDFcwB2-AH9VCvbEq_p8PCmdy54JwmVvCiVjH5" +
                "JYn8XTDgX20xqCbbuFZAh3gmG895uE518c9oRw_8m_a_wKBgQC-dx3vbOqXyLWKe_Nayo9M0spPlWErXex70HIeW6" +
                "6lOKd3fiFZWqE81nAfzfch6V7qdLDUG3mYIkKnu5i9qzmp9wt0UkXng98uqfWKulXIIUfmQLjiV8DC3k3hqyTOpLj" +
                "pR3rI_C9VFYfBGMWS4gqGV2NiNtLy2BE2hjKW7xDywQKBgC-yfOygfO0VvHmw7RXg5MueJ55os-7wvAoh6pB-14Dm" +
                "9E3jcsb1aFGuLSa6YHS1CTe5UkqPsjIKo5mN4cxXQi2OvILYtYwVAuWi2Hf3M1RFweIioa6s6GkCo_LY_SMBUhQ9A" +
                "i1bHXwOo5mqilzfyuyJpuTjdvxeozM2MGzNRq95AoGACxBiVcku63hGS3Ad19VSc7T--ZaE2X8QQLUAHBFZWNGssL" +
                "1L9KPWH_GY-8_8HiUvVVFIAFpEOvkqhBHaspHivKPUL4Kj-unnKg_HarKeTwzX32E9HDDayrcdMRG_Bp38-9giItC" +
                "7cybYBviIaZrl353t8QOcR20TuuYaxDfI9UECgYBCkTYl6V2KzZaIDEKIVqkWwVnlaCd8BoHAH40BACZYEF1VQDV7" +
                "NqvyPEuG0TMoEsUbaQsLgeygUoqeH6puxGhNu3ZmbeguCIL4lmRxotiqsZNq2XAb-VC7Wc3BVukPiEjzyyhSkmpUY" +
                "4ltylX4TyWjSDBV_LyDeSeSRQgJ21QFfA";

        PackageConstructor packageConstructor=new PackageConstructor();
        Source source=new Source("user1","127.0.0.1");
        Destination destination=new Destination("AS","127.3.4.1");
        //Date data=new Data();
        String test=packageConstructor.getPackageServiceResponse("Verify","Request",source,destination,"0000",pulickey);
        System.out.println(test);

    }



}

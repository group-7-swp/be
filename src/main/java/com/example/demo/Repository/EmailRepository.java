package com.example.demo.repository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.example.demo.model.OrderAndOrderItem;
import com.example.demo.model.ProductAndOrderItem;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.google.api.services.gmail.model.Message;
import org.apache.tomcat.util.codec.binary.Base64;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static com.google.api.services.gmail.GmailScopes.GMAIL_SEND;

public class EmailRepository {
    private static Credential getCredentials(final NetHttpTransport httpTransport, GsonFactory jsonFactory)
            throws IOException {
        // Load client secrets.
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(com.example.demo.repository.EmailRepository.class.getResourceAsStream("/client_secret_149572808832-f8smh3quca3tbioou13kldfouaeid7jv.apps.googleusercontent.com.json")));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, Set.of(GMAIL_SEND))
                .setDataStoreFactory(new FileDataStoreFactory(Paths.get("tokens").toFile()))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static boolean sendEmail(String subject, String message, String userEmail) throws Exception {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GsonFactory jsonFactory = GsonFactory.getDefaultInstance();
        Gmail service = new Gmail.Builder(httpTransport, jsonFactory, getCredentials(httpTransport, jsonFactory))
                .setApplicationName("Mail xác nhận đơn hàng")
                .build();

        // Encode as MIME message
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress("cskh.tiemhommie@gmail.com"));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(userEmail)); // change send to email address
        email.setSubject(subject);
        email.setContent(message, "text/html; charset=utf-8");

        // Encode and wrap the MIME message into a gmail message
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message msg = new Message();
        msg.setRaw(encodedEmail);

        try {
            // Create send message
            msg = service.users().messages().send("me", msg).execute();
            //System.out.println("Message id: " + msg.getId());
            //System.out.println(msg.toPrettyString());
            return true;
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Unable to send message: " + e.getDetails());
            } else {
                throw e;
            }
            return false;
        }
    }

    public static String messageCreate(int orderId){
        String htmlMessage = "";
        NumberFormat numberFormat = NumberFormat.getInstance();
        try {
            OrderAndOrderItem orderAndOrderItem = OrderDetailsRepository.getOrderDetailsByOrderId(orderId);
            if(orderAndOrderItem.getOrderId() != 0){
                List<ProductAndOrderItem> productAndOrderItemList = orderAndOrderItem.getProductAndOrderItemList();
                String item = "";
                for (ProductAndOrderItem productAndOrderItem:productAndOrderItemList) {
                    String productAndOrderItemStr =
                            "<tr>" +
                            "   <td style='padding-left: 10px'>" + productAndOrderItem.getProduct().getProductName() + "</td>" +
                            "   <td style='padding-left: 10px'>" + productAndOrderItem.getQuantity() + "</td>" +
                            "   <td style='padding-left: 10px'>" + numberFormat.format(productAndOrderItem.getProduct().getPrice()) + "</td>" +
                            "</tr>";

                    item = item  + productAndOrderItemStr;
                }
                if(orderAndOrderItem.getNote() == null) orderAndOrderItem.setNote("Không có");

                htmlMessage =
                        "<html>" +
                        "   <body>" +
                        "       Cảm ơn bạn đã đặt hàng tại Hommie!<br>" +
                        "       Chăm sóc khách hàng của Hommie sẽ liên hệ với quý khách trong 12h. Nếu quý khách gặp vấn đề hoặc có thắc mắc hãy liên hệ với Hommie qua số điện thoại 012.345.6789.<br>" +
                        "       <h4>*** THÔNG TIN ĐƠN HÀNG ***</h4>" +
                        "       Ngày đặt hàng: " + orderAndOrderItem.getOrderDate().toString() + "<br>" +
                        "       Mã đơn hàng: " + orderAndOrderItem.getOrderId() + "<br>" +
                        "       Người đặt hàng: " + orderAndOrderItem.getUser().getUserName() + "<br>" +
                        "       Phương thức thanh toán: " + orderAndOrderItem.getPayment().getPaymentType() + "<br>" +
                        "       Địa chỉ nhận hàng: " + orderAndOrderItem.getDelivery().getAddress() + "<br>" +
                        "       Lưu ý cho Hommie: " + orderAndOrderItem.getNote() + "<br>" +
                        "       Phí vận chuyển: " + numberFormat.format(orderAndOrderItem.getPayment().getPaymentCost()) + "<br>" +
                        "       Sản phẩm:<br>" +
                        "       <table style='width:80%' border='1' cellspacing='0'>" +
                        "           <tr>" +
                        "               <th>Tên sản phẩm</th>" +
                        "               <th>Số lượng</th>" +
                        "               <th>Đơn giá</th>" +
                        "           </tr>" + item +
                        "       </table><br>" +
                        "       Tổng tiền: " + numberFormat.format(orderAndOrderItem.getTotalPayment()) + "<br>" +
                        "       Chúc bạn một ngày vui vẻ!" +
                        "       <h4>Hommie</h4>" +
                        "   </body>" +
                        "</html>";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return htmlMessage;
    }
}

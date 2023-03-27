package bolk_app.services;

import bolk_app.models.Order;
import bolk_app.models.Recipient;
import bolk_app.models.Unit;
import bolk_app.repositories.RecipientRepo;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class XMLBuilder {

    public static void build(Order order, List<Unit> units, RecipientRepo recipientRepo) {
        Document doc = new Document();
        Element message = new Element("Message");
        buildMessageHeader(message);
        buildOrders(message, order, units, recipientRepo);
        doc.setRootElement(message);
        XMLOutputter outputter = new XMLOutputter();
        outputter.setFormat(Format.getPrettyFormat());
        try {
            outputter.output(doc, new FileWriter(new File("src/main/resources/result-xml.xml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void buildMessageHeader(Element message) {
        Element header = new Element("Message_Header");
        List<Element> parameters = Arrays.asList(
                new Element("Message_Date").setText(java.time.LocalDate.now().toString()),
                new Element("Message_Depot"),
                new Element("Message_Client"),
                new Element("Message_Time")
        );
        addToElement(header, parameters);
        message.addContent(header);
    }

    public static void buildOrders(Element message, Order order, List<Unit> units, RecipientRepo recipientRepo) {
        Element orders = new Element("Orders");
        Element orderEl = new Element("Order");
        orderEl.addContent(new Element("PickupAddress"));
        orderEl.addContent(new Element("DeliveryAddress"));
        orderEl.addContent(new Element("Goods_Details"));

        buildPickupAddress(orderEl.getChild("PickupAddress"));
        System.err.println(order.getRecipient().getId());
        buildDeliveryAddress(orderEl.getChild("DeliveryAddress"), recipientRepo, order.getRecipient().getId());
        buildGoodsDetails(orderEl.getChild("Goods_Details"), units);

        orders.addContent(orderEl);
        message.addContent(orders);
    }

    public static void buildPickupAddress(Element pickupAddress) {
        List<Element> pickupParameters = Arrays.asList(
                new Element("Name").setText("Bolk Transport B.V."),
                new Element("Street").setText("Zuidelijke Havenweg"),
                new Element("Nr").setText("4"),
                new Element("ZipCode").setText("7554RR"),
                new Element("City").setText("Hengelo"),
                new Element("Country").setText("NL"),
                new Element("Date").setText(java.time.LocalDate.now().toString())
        );
        addToElement(pickupAddress, pickupParameters);
    }

    public static void buildDeliveryAddress(Element deliveryAddress, RecipientRepo recipientRepo, int orderRecipientId) {
        Recipient recipient = recipientRepo.getRecipientByOrderId(orderRecipientId);
        List<Element> deliveryParameters = Arrays.asList(
                new Element("Name").setText(recipient.getName()),
                new Element("Street").setText(recipient.getStreet()),
                new Element("Nr").setText(recipient.getHouseNr()),
                new Element("ZipCode").setText(recipient.getZipCode()),
                new Element("City").setText(recipient.getCity()),
                new Element("Country").setText(recipient.getCountryCode()),
                new Element("Date").setText(java.time.LocalDate.now().toString())
        );
        addToElement(deliveryAddress, deliveryParameters);
    }

    public static void buildGoodsDetails(Element goodsDetails, List<Unit> units) {
        List<Element> goodsDetailList = new ArrayList<>();
        for (Unit u : units) {
            Element goodsDetail = new Element("Goods_Detail");
            Element packageQuantity = new Element("PackageQuantity").setText("1");
            Element packageUnitCode = new Element("PackageUnitCode").setText(u.getType().name());
            Element goodsWeight = new Element("Goods_Weight").setText(String.format("%.2f", u.getWeight()));
            Element collo = new Element("Collo_Detail");
            List<Element> colloParams = Arrays.asList(
                    new Element("Collo_Length").setText(Integer.toString(Math.round(u.getLength()))),
                    new Element("Collo_Width").setText(Integer.toString(Math.round(u.getWidth()))),
                    new Element("Collo_Height").setText(Integer.toString(Math.round(u.getHeight()))),
                    new Element("Collo_Weight").setText(String.format("%.2f", u.getWeight())),
                    new Element("Collo_PalletUnitCode").setText(u.getType().name())
            );
            addToElement(collo, colloParams);
            goodsDetail.addContent(packageQuantity);
            goodsDetail.addContent(packageUnitCode);
            goodsDetail.addContent(goodsWeight);
            goodsDetail.addContent(collo);
            goodsDetailList.add(goodsDetail);
        }
        addToElement(goodsDetails, goodsDetailList);
    }

    public static void addToElement(Element e, List<Element> li) {
        li.forEach(e::addContent);
    }

    //private int numberofunits (Number of pallets/boxes)
    //receive string, convert to xml and save in a file
    //use adress of bolk, get adress for recipient from database
    //loop through units with numberofunits
    //split string on a character
}

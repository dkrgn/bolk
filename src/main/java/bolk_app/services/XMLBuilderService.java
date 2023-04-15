package bolk_app.services;

import bolk_app.models.Order;
import bolk_app.models.Recipient;
import bolk_app.models.Unit;
import bolk_app.repositories.RecipientRepo;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service class to build a XML file with provided Order and Unit data. It saves it to
 * src/main/resources/file-outputs/xml-order-config/ directory
 */
@Service
public class XMLBuilderService {

    private final RecipientRepo recipientRepo;

    @Autowired
    public XMLBuilderService(RecipientRepo recipientRepo) {
        this.recipientRepo = recipientRepo;
    }

    /**
     * Method to build root elements and call other method
     * @param order to be written
     * @param units to be written
     */
    public void build(Order order, List<Unit> units) {
        Document doc = new Document();
        Element message = new Element("Message");
        buildMessageHeader(message);
        buildOrders(message, order, units);
        doc.setRootElement(message);
        XMLOutputter outputter = new XMLOutputter();
        outputter.setFormat(Format.getPrettyFormat());
        try {
            outputter.output(doc, new FileWriter(new File("src/main/resources/file-outputs/xml-order-config/order-xml-" + order.getId() + ".xml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method build Message Header element
     * @param message root element
     */
    public void buildMessageHeader(Element message) {
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

    /**
     * Method to build Order tag elements
     * @param message root element
     * @param order to be written
     * @param units to be written
     */
    public void buildOrders(Element message, Order order, List<Unit> units) {
        Element orders = new Element("Orders");
        Element orderEl = new Element("Order");
        List<Element> orderParameters = Arrays.asList(
                new Element("Depot_Departure"),
                new Element("Depot_Arrival"),
                new Element("Waybill_NR"),
                new Element("Delivery_Type"),
                new Element("Resource_Code"),
                new Element("Bill_Reference"),
                new Element("COD_Amount"),
                new Element("InDelivery_Alert"),
                new Element("InDelivery_Mailaddress"),
                new Element("InDelivery_Mailaddress2"),
                new Element("InDelivery_Mailaddress3"),
                new Element("InDelivery_Mailaddress4"),
                new Element("InDelivery_Mailaddress5"),
                new Element("InDelivery_Mailaddress6"),
                new Element("InDelivery_Mailaddress7"),
                new Element("InDelivery_Mailaddress8"),
                new Element("InDelivery_Mailaddress9"),
                new Element("InDelivery_Mailaddress10"),
                new Element("InDelivery_Sms1"),
                new Element("InDelivery_Sms2"),
                new Element("InDelivery_Sms3"),
                new Element("InDelivery_Sms4"),
                new Element("InDelivery_Sms5"),
                new Element("InDelivery_Sms6"),
                new Element("InDelivery_Sms7"),
                new Element("InDelivery_Sms8"),
                new Element("InDelivery_Sms9"),
                new Element("InDelivery_Sms10"),
                new Element("PickupAddress"),
                new Element("DeliveryAddress"),
                new Element("Neutral"),
                new Element("TOD"),
                new Element("Instructions"),
                new Element("Original_Documents"),
                new Element("Groundplaces"),
                new Element("Goods_Details")

        );
        orderParameters.forEach(orderEl::addContent);

        Element resource = orderEl.getChild("Resource_Code");
        resource.addContent(new Element("RC_accepted"));
        resource.addContent(new Element("RC_not_accepted"));

        buildPickupAddress(orderEl.getChild("PickupAddress"));
        buildDeliveryAddress(orderEl.getChild("DeliveryAddress"), order.getRecipient().getId());
        buildGoodsDetails(orderEl.getChild("Goods_Details"), units);

        orders.addContent(orderEl);
        message.addContent(orders);
    }

    /**
     * Method to build pickup address element
     * @param pickupAddress root element
     */
    public void buildPickupAddress(Element pickupAddress) {
        List<Element> pickupParameters = Arrays.asList(
                new Element("Code"),
                new Element("Name").setText("Bolk Transport B.V."),
                new Element("Name2"),
                new Element("Street").setText("Zuidelijke Havenweg"),
                new Element("Nr").setText("4"),
                new Element("NrAddition"),
                new Element("PU_address_extra"),
                new Element("ZipCode").setText("7554RR"),
                new Element("City").setText("Hengelo"),
                new Element("Country").setText("NL"),
                new Element("Phone"),
                new Element("X_Coordinate"),
                new Element("Y_Coordinate"),
                new Element("Validate"),
                new Element("TimeOpen"),
                new Element("Date").setText(java.time.LocalDate.now().toString()),
                new Element("Time"),
                new Element("Loading_Reference"),
                new Element("Time_Frame_loading")
        );
        addToElement(pickupAddress, pickupParameters);

        Element tfl = pickupAddress.getChild("Time_Frame_loading");
        List<Element> tflParameters = Arrays.asList(
                new Element("FromTime_1"),
                new Element("UntilTime_1"),
                new Element("FromTime_2"),
                new Element("UntilTime_2")
        );
        addToElement(tfl, tflParameters);
    }

    /**
     * Method to build delivery address element
     * @param deliveryAddress root element
     * @param orderRecipientId id of Order to be written in file
     */
    public void buildDeliveryAddress(Element deliveryAddress, int orderRecipientId) {
        Recipient recipient = recipientRepo.getRecipientByOrderId(orderRecipientId);
        List<Element> deliveryParameters = Arrays.asList(
                new Element("Code"),
                new Element("Name").setText(recipient.getName()),
                new Element("Name2"),
                new Element("Street").setText(recipient.getStreet()),
                new Element("Nr").setText(recipient.getHouseNr()),
                new Element("NrAddition"),
                new Element("Del_address_extra"),
                new Element("ZipCode").setText(recipient.getZipCode()),
                new Element("City").setText(recipient.getCity()),
                new Element("Country").setText(recipient.getCountryCode()),
                new Element("Phone"),
                new Element("X_Coordinate"),
                new Element("Y_Coordinate"),
                new Element("Validate"),
                new Element("TimeOpen"),
                new Element("Preferred_unloading_date"),
                new Element("Date").setText(java.time.LocalDate.now().toString()),
                new Element("Time"),
                new Element("Unloading_Reference"),
                new Element("Time_Frame_unloading")
        );
        addToElement(deliveryAddress, deliveryParameters);

        Element tfl = deliveryAddress.getChild("Time_Frame_unloading");
        List<Element> tflParameters = Arrays.asList(
                new Element("FromTime_1"),
                new Element("UntilTime_1"),
                new Element("FromTime_2"),
                new Element("UntilTime_2")
        );
        addToElement(tfl, tflParameters);
    }

    /**
     * Method to build Order inner elements
     * @param goodsDetails root element
     * @param units to be written
     */
    public void buildGoodsDetails(Element goodsDetails, List<Unit> units) {
        List<Element> goodsDetailList = new ArrayList<>();
        for (Unit u : units) {
            Element collo = new Element("Collo_Detail");
            List<Element> colloParams = Arrays.asList(
                    new Element("Barcode"),
                    new Element("UnitDescription"),
                    new Element("Collo_Length").setText(Integer.toString(Math.round(u.getLength()))),
                    new Element("Collo_Width").setText(Integer.toString(Math.round(u.getWidth()))),
                    new Element("Collo_Height").setText(Integer.toString(Math.round(u.getHeight()))),
                    new Element("Collo_Weight").setText(String.format("%.2f", u.getWeight())),
                    new Element("Collo_LoadMeter"),
                    new Element("Collo_Pallet"),
                    new Element("Collo_PalletUnitCode").setText(u.getType().name())
            );
            addToElement(collo, colloParams);

            Element goodsDangerousGood = new Element("Goods_Dangerous_Good");
            List<Element> dangGoodParameters = Arrays.asList(
                    new Element("DGS_Unique_code"),
                    new Element("DGS_technical_name"),
                    new Element("DGS_neg"),
                    new Element("DGS_UN_nr"),
                    new Element("DGS_group"),
                    new Element("DGS_class"),
                    new Element("DGS_classification"),
                    new Element("DGS_etiket"),
                    new Element("DGS_tunnelcode"),
                    new Element("DGS_category"),
                    new Element("DGS_weight"),
                    new Element("DGS_multiply_factor"),
                    new Element("DGS_points")
            );
            addToElement(goodsDangerousGood, dangGoodParameters);

            Element goodsDetail = new Element("Goods_Detail");
            List<Element> parameters = Arrays.asList(
                    new Element("DeliveryNoteNumber"),
                    new Element("PackageQuantity").setText("1"),
                    new Element("PackageUnitCode").setText(u.getType().name()),
                    new Element("PackageUnitDescription"),
                    new Element("GoodsDescription"),
                    new Element("Goods_Weight").setText(String.format("%.2f", u.getWeight())),
                    new Element("Goods_Volume"),
                    new Element("Goods_LoadMeter"),
                    new Element("ShipmentQuantity"),
                    new Element("ShipmentUnitCode"),
                    new Element("ShipmentUnitDescription"),
                    new Element("EmballageQuantity"),
                    new Element("EmballageUnitCode"),
                    new Element("EmballageUnitDescription"),
                    collo,
                    goodsDangerousGood
            );
            addToElement(goodsDetail, parameters);
            goodsDetailList.add(goodsDetail);
        }
        addToElement(goodsDetails, goodsDetailList);
    }

    /**
     * Method to ease adding elements to other elements
     * @param e elements to add
     * @param li list where to add elements
     */
    public void addToElement(Element e, List<Element> li) {
        li.forEach(e::addContent);
    }
}

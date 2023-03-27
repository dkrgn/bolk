package bolk_app.services;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class XMLBuilder {

    public static void build() {
        Document doc = new Document();
        Element message = new Element("Message");
        buildMessageHeader(message);
        buildOrders(message);
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
                new Element("Message_Date"),
                new Element("Message_Depot"),
                new Element("Message_Client"),
                new Element("Message_Time")
        );
        addToElement(header, parameters);
        message.addContent(header);
    }

    public static void buildOrders(Element message) {
        Element orders = new Element("Orders");
        Element order = new Element("Order");
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
        orderParameters.forEach(order::addContent);

        Element resource = order.getChild("Resource_Code");
        resource.addContent(new Element("RC_accepted"));
        resource.addContent(new Element("RC_not_accepted"));

        buildPickupAddress(order.getChild("PickupAddress"));
        buildDeliveryAddress(order.getChild("DeliveryAddress"));
        buildGoodsDetails(order.getChild("Goods_Details"));

        orders.addContent(order);
        message.addContent(orders);
    }

    public static void buildPickupAddress(Element pickupAddress) {
        List<Element> pickupParameters = Arrays.asList(
                new Element("Code"),
                new Element("Name"),
                new Element("Name2"),
                new Element("Street"),
                new Element("Nr"),
                new Element("NrAddition"),
                new Element("PU_address_extra"),
                new Element("ZipCode"),
                new Element("City"),
                new Element("Country"),
                new Element("Phone"),
                new Element("X_Coordinate"),
                new Element("Y_Coordinate"),
                new Element("Validate"),
                new Element("TimeOpen"),
                new Element("Date"),
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
        tflParameters.forEach(tfl::addContent);
    }

    public static void buildDeliveryAddress(Element deliveryAddress) {
        List<Element> deliveryParameters = Arrays.asList(
                new Element("Code"),
                new Element("Name"),
                new Element("Name2"),
                new Element("Street"),
                new Element("Nr"),
                new Element("NrAddition"),
                new Element("Del_address_extra"),
                new Element("ZipCode"),
                new Element("City"),
                new Element("Country"),
                new Element("Phone"),
                new Element("X_Coordinate"),
                new Element("Y_Coordinate"),
                new Element("Validate"),
                new Element("TimeOpen"),
                new Element("Preferred_unloading_date"),
                new Element("Date"),
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
        tflParameters.forEach(tfl::addContent);
    }

    public static void buildGoodsDetails(Element goodsDetails) {
        Element goodsDetail = new Element("Goods_Detail");
        List<Element> parameters = Arrays.asList(
                new Element("DeliveryNoteNumber"),
                new Element("PackageQuantity"),
                new Element("PackageUnitCode"),
                new Element("PackageUnitDescription"),
                new Element("GoodsDescription"),
                new Element("Goods_Weight"),
                new Element("Goods_Volume"),
                new Element("Goods_LoadMeter"),
                new Element("ShipmentQuantity"),
                new Element("ShipmentUnitCode"),
                new Element("ShipmentUnitDescription"),
                new Element("EmballageQuantity"),
                new Element("EmballageUnitCode"),
                new Element("EmballageUnitDescription"),
                new Element("Collo_Detail"),
                new Element("Goods_Dangerous_Goods")
        );
        addToElement(goodsDetail, parameters);

        List<Element> collo = Arrays.asList(
                new Element("Barcode"),
                new Element("UnitDescription"),
                new Element("Collo_Length"),
                new Element("Collo_Width"),
                new Element("Collo_Height"),
                new Element("Collo_Weight"),
                new Element("Collo_LoadMeter"),
                new Element("Collo_Pallet"),
                new Element("Collo_PalletUnitCode")
        );
        addToElement(goodsDetail.getChild("Collo_Detail"), collo);

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

        goodsDetail.getChild("Goods_Dangerous_Goods").addContent(goodsDangerousGood);
        goodsDetails.addContent(goodsDetail);
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

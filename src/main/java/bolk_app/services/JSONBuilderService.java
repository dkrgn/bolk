package bolk_app.services;

import bolk_app.models.Order;
import bolk_app.models.Recipient;
import bolk_app.models.Unit;
import bolk_app.repositories.RecipientRepo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class JSONBuilderService {

    private final RecipientRepo recipientRepo;

    public void build(Order order, List<Unit> units) {
        JSONObject root = new JSONObject();
        reflect(root);
        JSONObject message = new JSONObject();
        reflect(message);
        buildMessageHeader(message);
        buildOrders(message, units, order);
        root.put("Message", message);
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement je = JsonParser.parseString(root.toString());
            String prettyJsonString = gson.toJson(je);
            FileWriter writer = new FileWriter("src/main/resources/file-outputs/json-order-config/order-json-" + order.getId() + ".json");
            writer.write(prettyJsonString);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        };
    }

    private void reflect(JSONObject jsonObject) {
        try {
            Field changeMap = jsonObject.getClass().getDeclaredField("map");
            changeMap.setAccessible(true);
            changeMap.set(jsonObject, new LinkedHashMap<>());
            changeMap.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void buildMessageHeader(JSONObject root) {
        JSONObject messageHeader = new JSONObject();
        reflect(messageHeader);
        messageHeader.put("Message_Date", java.time.LocalDate.now().toString());
        messageHeader.put("Message_Depot", "");
        messageHeader.put("Message_Client", "");
        messageHeader.put("Message_Time", "");
        root.put("Message_Header", messageHeader);
    }

    private void buildOrders(JSONObject root, List<Unit> units, Order o) {
        JSONObject order = new JSONObject();
        reflect(order);
        order.put("Depot_Departure", "");
        order.put("Depot_Arrival", "");
        order.put("Waybill_NR", "");
        order.put("Delivery_Type", "");
        JSONObject rsCode = new JSONObject();
        reflect(rsCode);
        rsCode.put("RC_accepted", "");
        rsCode.put("RC_not_accepted", "");
        order.put("Resource_Code", rsCode);
        order.put("Bill_Reference", "");
        order.put("COD_Amount", "");
        order.put("InDelivery_Alert", "");
        order.put("InDelivery_Mailaddress", "");
        order.put("InDelivery_Mailaddress2", "");
        order.put("InDelivery_Mailaddress3", "");
        order.put("InDelivery_Mailaddress4", "");
        order.put("InDelivery_Mailaddress5", "");
        order.put("InDelivery_Mailaddress6", "");
        order.put("InDelivery_Mailaddress7", "");
        order.put("InDelivery_Mailaddress8", "");
        order.put("InDelivery_Mailaddress9", "");
        order.put("InDelivery_Mailaddress10", "");
        order.put("InDelivery_Sms1", "");
        order.put("InDelivery_Sms2", "");
        order.put("InDelivery_Sms3", "");
        order.put("InDelivery_Sms4", "");
        order.put("InDelivery_Sms5", "");
        order.put("InDelivery_Sms6", "");
        order.put("InDelivery_Sms7", "");
        order.put("InDelivery_Sms8", "");
        order.put("InDelivery_Sms9", "");
        order.put("InDelivery_Sms10", "");
        order.put("PickupAddress", buildPickUpAddress());
        order.put("DeliveryAddress", buildDeliveryAddress(o.getRecipient().getId()));
        order.put("Neutral", "");
        order.put("TOD", "");
        order.put("Instructions", "");
        order.put("Original_Documents", "");
        order.put("Groundplaces", "");
        order.put("Goods_Details", buildGoodsDetails(units));
        JSONObject orders = new JSONObject();
        reflect(orders);
        orders.put("Order", order);
        root.put("Orders", orders);
    }

    private JSONObject buildPickUpAddress() {
        JSONObject loading = new JSONObject();
        reflect(loading);
        loading.put("FromTime_1", "");
        loading.put("UntilTime_1", "");
        loading.put("FromTime_2", "");
        loading.put("UntilTime_2", "");

        JSONObject pickup = new JSONObject();
        reflect(pickup);
        pickup.put("Code", "");
        pickup.put("Name", "Bolk Transport B.V.");
        pickup.put("Name2", "");
        pickup.put("Street", "Zuidelijke Havenweg");
        pickup.put("Nr", "4");
        pickup.put("NrAddition", "");
        pickup.put("PU_address_extra", "");
        pickup.put("ZipCode", "7554RR");
        pickup.put("City", "Hengelo");
        pickup.put("Country", "NL");
        pickup.put("Phone", "");
        pickup.put("X_Coordinate", "");
        pickup.put("Y_Coordinate", "");
        pickup.put("Validate", "");
        pickup.put("TimeOpen", "");
        pickup.put("Date", java.time.LocalDate.now().toString());
        pickup.put("Time", "");
        pickup.put("Loading_Reference", "");
        pickup.put("Time_Frame_loading", loading);

        return pickup;
    }

    private JSONObject buildDeliveryAddress(int orderRecipientId) {
        Recipient recipient = recipientRepo.getRecipientByOrderId(orderRecipientId);
        JSONObject unloading = new JSONObject();
        reflect(unloading);
        unloading.put("FromTime_1", "");
        unloading.put("UntilTime_1", "");
        unloading.put("FromTime_2", "");
        unloading.put("UntilTime_2", "");

        JSONObject delivery = new JSONObject();
        reflect(delivery);
        delivery.put("Code", "");
        delivery.put("Name", recipient.getName());
        delivery.put("Name2", "");
        delivery.put("Street", recipient.getStreet());
        delivery.put("Nr", recipient.getHouseNr());
        delivery.put("NrAddition", "");
        delivery.put("PU_address_extra", "");
        delivery.put("ZipCode", recipient.getZipCode());
        delivery.put("City", recipient.getCity());
        delivery.put("Country", recipient.getCountryCode());
        delivery.put("Phone", "");
        delivery.put("X_Coordinate", "");
        delivery.put("Y_Coordinate", "");
        delivery.put("Validate", "");
        delivery.put("TimeOpen", "");
        delivery.put("Date", java.time.LocalDate.now().toString());
        delivery.put("Time", "");
        delivery.put("Loading_Reference", "");
        delivery.put("Time_Frame_unloading", unloading);

        return delivery;
    }

    private JSONArray buildGoodsDetails(List<Unit> units) {
        JSONArray goodsArray = new JSONArray();
        for (Unit u : units) {
            JSONObject collo = new JSONObject();
            reflect(collo);
            collo.put("Barcode", "");
            collo.put("UnitDescription", "");
            collo.put("Collo_Length", Integer.toString(Math.round(u.getLength())));
            collo.put("Collo_Width", Integer.toString(Math.round(u.getWidth())));
            collo.put("Collo_Height", Integer.toString(Math.round(u.getHeight())));
            collo.put("Collo_Weight", String.format("%.2f", u.getWeight()));
            collo.put("Collo_LoadMeter", "");
            collo.put("Collo_Pallet", "");
            collo.put("Collo_PalletUnitCode", u.getType().name());

            JSONObject dangParam = new JSONObject();
            reflect(dangParam);
            dangParam.put("DGS_Unique_code", "");
            dangParam.put("DGS_technical_name", "");
            dangParam.put("DGS_neg", "");
            dangParam.put("DGS_UN_nr", "");
            dangParam.put("DGS_group", "");
            dangParam.put("DGS_class", "");
            dangParam.put("DGS_classification", "");
            dangParam.put("DGS_etiket", "");
            dangParam.put("DGS_tunnelcode", "");
            dangParam.put("DGS_category", "");
            dangParam.put("DGS_weight", "");
            dangParam.put("DGS_multiply_factor", "");
            dangParam.put("DGS_points", "");

            JSONObject dangGoods = new JSONObject();
            reflect(dangGoods);
            dangGoods.put("Goods_Dangerous_Good", dangParam);

            JSONObject goodsDetail = new JSONObject();
            reflect(goodsDetail);
            goodsDetail.put("DeliveryNoteNumber", "");
            goodsDetail.put("PackageQuantity", "1");
            goodsDetail.put("PackageUnitCode",u.getType().name());
            goodsDetail.put("PackageUnitDescription", "");
            goodsDetail.put("GoodsDescription", "");
            goodsDetail.put("Goods_Weight", String.format("%.2f", u.getWeight()));
            goodsDetail.put("Goods_Volume", "");
            goodsDetail.put("Goods_LoadMeter", "");
            goodsDetail.put("ShipmentQuantity", "");
            goodsDetail.put("ShipmentUnitCode", "");
            goodsDetail.put("ShipmentUnitDescription", "");
            goodsDetail.put("EmballageQuantity", "");
            goodsDetail.put("EmballageUnitCode", "");
            goodsDetail.put("EmballageUnitDescription", "");
            goodsDetail.put("Collo_Detail", collo);
            goodsDetail.put("Goods_Dangerous_Goods", dangGoods);
            JSONObject goodsDetails = new JSONObject();
            goodsDetails.put("Goods_Detail", goodsDetail);
            goodsArray.put(goodsDetails);
        }
        return goodsArray;
    }
}

package bolk_app.services;

import org.springframework.stereotype.Service;

@Service
public class XMLBuilder {
    private int numberofunits = 0;
    public String build() {
        <?xml version="1.0" encoding="UTF-8" ?>
        <PickupAddress>
                <Name>Bolk Transport B.V.</Name>
                <Street>Zuidelijke Havenweg</Street>
                <Nr>4</Nr>
                <Zipcode>7554RR</Zipcode>
                <City>Hengelo</City>
                <Country>NL</Country>
                <Date>java.time.LocalDate.now()</Date>
        <PickupAddress/>

        <DeliveryAdress>
                <Name> </Name>
                <Street> </Street>
                <Nr> </Nr>
                <Zipcode> </Zipcode>
                <City> </City>
                <Country> </Country>
                <Date>java.time.LocalDate.now()</Date>
        <PickupAddress/>

        <Goods_Details>
            <Goods_Detail>
                <PackageQuantity> </PackageQuantity>
                <PackageUnitCode> </PackageUnitCode> //Pallettype / Box
                <Goods_Weight></Goods_Weight> //in kg
                <Collo Detail>
                    <Collo_Length> </Collo_Length> //in cm
                    <Collo_Width> </Collo_Width> //in cm
                    <Collo_Heigth> </Collo_Heigth> //in cm
                    <Collo_Weigth> </Collo_Weigth> //in kg, same as Goods_Weight
                    <Collo_PalletUnitCode> </Collo_PalletsUnitCode>
                </Collo Detail>
            </Goods_Detail>
        </Goods_Details>


        return null;
    }
    //private int numberofunits (Number of pallets/boxes)
    //receive string, convert to xml and save in a file
    //use adress of bolk, get adress for recipient from database
    //loop through units with numberofunits
    //split string on a character
}

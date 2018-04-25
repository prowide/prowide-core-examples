/*******************************************************************************
 * Copyright (c) 2016 Prowide Inc.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as 
 *     published by the Free Software Foundation, either version 3 of the 
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 *     
 *     Check the LGPL at <http://www.gnu.org/licenses/> for more details.
 *******************************************************************************/
package com.prowidesoftware.swift.samples.core;

import java.io.IOException;

import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field61;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;

/**
 * This example shows how to read a SWIFT MT message from a String, in the context where
 * the message type to parse is already known, in the example we use an MT 940.
 * 
 * Running this program produces the following output:
<pre>
Sender: BBBBAA33XXXX
Receiver: AAAABB99BSMK
Sender's Reference: 0112230000000890
Amount: 110,92
Transaction Type: NDIV
Reference Acc Owner: NONREF
Amount: 3519,76
Transaction Type: NTRF
Reference Acc Owner: 50RS201403240008
</pre>
 * 
 * @author www.prowidesoftware.com
 * @since 7.7
 */
public class ParseMT940FromStringExample {

	public static void main(String[] args) throws IOException {
        /*
         * A simple String containing the message content to parse
         */
        String msg  = "{1:F01AAAABB99BSMK3513951576}"+
                "{2:O9400934081223BBBBAA33XXXX03592332770812230834N}" +
                "{4:\n"+
                ":20:0112230000000890\n"+
                ":25:SAKG800030155USD\n"+
                ":28C:255/1\n"+
                ":60F:C011223USD175768,92\n"+
                ":61:0112201223CD110,92NDIVNONREF//08 IL053309\n"+
                "/GB/2542049/SHS/312,\n"+
                ":62F:C011021USD175879,84\n"+
                ":20:NONREF\n" +
                ":25:4001400010\n" +
                ":28C:58/1\n" +
                ":60F:C140327EUR6308,75\n" +
                ":61:1403270327C3519,76NTRF50RS201403240008//2014032100037666\n" +
                "ABC DO BRASIL LTDA\n" +
                ":86:INVOICE NR. 6000012801 \n" +
                "ORDPRTY : ABC DO BRASIL LTDA RUA LIBERO BADARO,293-SAO \n" +
                "PAULO BRAZIL }";
        /*
		 * Parse the String content into a SWIFT message object
		 */
		MT940 mt = MT940.parse(msg);
		
		/*
		 * Print header information
		 */
		System.out.println("Sender: "+mt.getSender());
		System.out.println("Receiver: "+mt.getReceiver());
		
		/*
		 * Print details of a specific field
		 */
		Field20 f = mt.getField20();
		System.out.println(Field.getLabel(f.getName(), mt.getMessageType(), null) + ": "+f.getReference());
		
		for (Field61 tx : mt.getField61()) {
			System.out.println("Amount: "+tx.getComponent(Field61.AMOUNT));
			System.out.println("Transaction Type: "+tx.getComponent(Field61.TRANSACTION_TYPE));
			//System.out.println("Identification: "+tx.getComponent(Field61.IDENTIFICATION_CODE)); //since version 7.8
			System.out.println("Reference Acc Owner: "+tx.getComponent(Field61.REFERENCE_FOR_THE_ACCOUNT_OWNER));
		}
	}
}

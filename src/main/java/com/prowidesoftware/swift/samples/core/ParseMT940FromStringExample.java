/*
 * Copyright 2006-2021 Prowide
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.prowidesoftware.swift.samples.core;

import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field61;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;

/**
 * This example shows how to read a SWIFT MT message from a String, in the context where
 * the message type to parse is already known, in the example we use an MT 940.
 * <p>
 * Running this program produces the following output:
 * <pre>
 * Sender: BBBBAA33XXXX
 * Receiver: AAAABB99BSMK
 * Sender's Reference: 0112230000000890
 * Amount: 110,92
 * Transaction Type: NDIV
 * Reference Acc Owner: NONREF
 * Amount: 3519,76
 * Transaction Type: NTRF
 * Reference Acc Owner: 50RS201403240008
 * </pre>
 */
public class ParseMT940FromStringExample {

    public static void main(String[] args) {
        /*
         * A simple String containing the message content to parse
         */
        String msg = "{1:F01AAAABB99BSMK3513951576}" +
                "{2:O9400934081223BBBBAA33XXXX03592332770812230834N}" +
                "{4:\n" +
                ":20:0112230000000890\n" +
                ":25:SAKG800030155USD\n" +
                ":28C:255/1\n" +
                ":60F:C011223USD175768,92\n" +
                ":61:0112201223CD110,92NDIVNONREF//08 IL053309\n" +
                "/GB/2542049/SHS/312,\n" +
                ":62F:C011021USD175879,84\n" +
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
        System.out.println("Sender: " + mt.getSender());
        System.out.println("Receiver: " + mt.getReceiver());

        /*
         * Print details of a specific field
         */
        Field20 f = mt.getField20();
        System.out.println(Field.getLabel(f.getName(), mt.getMessageType(), null) + ": " + f.getReference());

        for (Field61 tx : mt.getField61()) {
            System.out.println("Amount: " + tx.getComponent(Field61.AMOUNT));
            System.out.println("Transaction Type: " + tx.getComponent(Field61.TRANSACTION_TYPE));
            //System.out.println("Identification: "+tx.getComponent(Field61.IDENTIFICATION_CODE)); //since version 7.8
            System.out.println("Reference Acc Owner: " + tx.getComponent(Field61.REFERENCE_FOR_THE_ACCOUNT_OWNER));
        }
    }
}

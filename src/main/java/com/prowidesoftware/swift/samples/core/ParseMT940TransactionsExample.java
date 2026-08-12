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

import com.prowidesoftware.swift.model.SwiftTagListBlock;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field61;
import com.prowidesoftware.swift.model.mt.mt9xx.MT940;

/**
 * This example shows how to link the non-sequence loop conformed by fields 61 and 86
 * in and MT940 statement, printing information of the transaction information in
 * field 61 along with the description in the corresponding fields 86 (if present).
 * <p>
 * Running this program produces the following output:
 * <pre>
 * ---------------------------
 * Amount: 110,92
 * Transaction Type: NDIV
 * Reference Acc Owner: NONREF
 * ---------------------------
 * Amount: 50000000,
 * Transaction Type: NTRF
 * Reference Acc Owner: NONREF
 * ---------------------------
 * Amount: 200000,
 * Transaction Type: NDIV
 * Reference Acc Owner: NONREF
 * Description: DIVIDEND FOO CORP
 * PREFERRED STOCK 1ST QUARTER 1998
 * ---------------------------
 * Amount: 5700000,
 * Transaction Type: NFEX
 * Reference Acc Owner: 036960
 * Description: FOO INC
 * </pre>
 */
public class ParseMT940TransactionsExample {

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
                ":61:980623C50000000,NTRFNONREF//9999234\n" +
                "ORDER BK OF NYC FOO CASH RESERVE\n" +
                ":61:980626C200000,NDIVNONREF//9999543\n" +
                ":86:DIVIDEND FOO CORP\n" +
                "PREFERRED STOCK 1ST QUARTER 1998\n" +
                ":61:980625C5700000,NFEX036960//8954321\n" +
                ":86:FOO INC\n" +
                ":62F:C011021USD175879,84\n" +
                ":20:NONREF\n" +
                ":25:4001400010\n" +
                ":28C:58/1\n" +
                ":60F:C140327EUR6308,75\n" +
                ":61:1403270327C3519,76NTRF50RS201403240008//2014032100037666\n" +
                "ABC DO BRASIL LTDA\n" +
                ":86:INVOICE NR. 6000012801 \n" +
                "ORDPRTY : ABC LTDA RUA LIBERO BADARO,293-SAO \n" +
                "PAULO BRAZIL }";
        /*
         * Parse the String content into a SWIFT message object
         */
        MT940 mt = MT940.parse(msg);

        /*
         * Get the loop between 60[F,M] and 62[F,M]
         */
        Tag start = mt.getSwiftMessage().getBlock4().getTagByNumber(60);
        Tag end = mt.getSwiftMessage().getBlock4().getTagByNumber(62);
        SwiftTagListBlock loop = mt.getSwiftMessage().getBlock4().getSubBlock(start, end);
        for (int i = 0; i < loop.size(); i++) {
            Tag t = loop.getTag(i);
            if (t.getName().equals("61")) {
                Field61 tx = (Field61) t.asField();
                System.out.println("---------------------------");
                System.out.println("Amount: " + tx.getComponent(Field61.AMOUNT));
                System.out.println("Transaction Type: " + tx.getComponent(Field61.TRANSACTION_TYPE));
                //System.out.println("Identification: "+tx.getComponent(Field61.IDENTIFICATION_CODE)); //since version 7.8
                System.out.println("Reference Acc Owner: " + tx.getComponent(Field61.REFERENCE_FOR_THE_ACCOUNT_OWNER));
                /*
                 * look ahead for field 86
                 */
                if (i + 1 < loop.size() && loop.getTag(i + 1).getName().equals("86")) {
                    System.out.println("Description: " + loop.getTag(i + 1).getValue());
                    i++;
                }
            }
        }

    }
}

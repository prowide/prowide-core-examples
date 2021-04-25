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

import com.prowidesoftware.swift.model.*;
import com.prowidesoftware.swift.model.field.*;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;

import java.util.Calendar;

/**
 * Example of message creation including explicit content for blocks 3 and S.
 * An MT103 is created and converted to the SWIFT FIN format.
 * <p>
 * Running this program produces the following SWIFT FIN message content:
 * <pre>
 * {1:F01BICFOOYYAXXX1234123456}{2:I103BICFOARXXXXXN1}{3:{119:STP}}{4:
 * :20:REFERENCE
 * :23B:CRED
 * :32A:150116EUR1234567,89
 * :50A:/12345678901234567890
 * FOOBANKXXXXX
 * :59:/12345678901234567890
 * JOE DOE
 * :71A:OUR
 * -}{5:{MAC:00000000}{PDE:}}{S:{SAC:}{COP:P}}
 * </pre>
 */
public class MessageCreation3Example {

    /**
     * This example creates a new MT103 using MT and Field helper classes.
     */
    public static void main(String[] args) {
        /*
         * Create the MT class, it will be initialized as an outgoing message with normal priority
         */
        final MT103 m = new MT103();

        /*
         * Create and set a specific header block 1
         */
        SwiftBlock1 b1 = new SwiftBlock1();
        b1.setApplicationId("F");
        b1.setServiceId("01");
        b1.setLogicalTerminal("BICFOOYYAXXX");
        b1.setSessionNumber("1234");
        b1.setSequenceNumber("123456");
        m.getSwiftMessage().setBlock1(b1);

        /*
         * Create and set a specific header block 2
         * Notice there are two block 2 headers (for input and output messages)
         */
        SwiftBlock2Input b2 = new SwiftBlock2Input();
        b2.setMessageType("103");
        b2.setReceiverAddress("BICFOARXXXXX");
        b2.setDeliveryMonitoring("1");
        m.getSwiftMessage().setBlock2(b2);

        /*
         * Add the optional user header block
         */
        SwiftBlock3 block3 = new SwiftBlock3();
        block3.append(new Field119("STP"));
        m.getSwiftMessage().addBlock(block3);

        /*
         * Start adding the message's fields in correct order
         */
        m.addField(new Field20("REFERENCE"));
        m.addField(new Field23B("CRED"));

        /*
         * Add a field using comprehensive setters API
         */
        Field32A f32A = new Field32A()
                .setDate(Calendar.getInstance())
                .setCurrency("EUR")
                .setAmount("1234567,89");
        m.addField(f32A);

        /*
         * Add the orderer field
         */
        Field50A f50A = new Field50A()
                .setAccount("12345678901234567890")
                .setBIC("FOOBANKXXXXX");
        m.addField(f50A);

        /*
         * Add the beneficiary field
         */
        Field59 f59 = new Field59()
                .setAccount("12345678901234567890")
                .setNameAndAddress("JOE DOE");
        m.addField(f59);

        /*
         * Add the commission indication
         */
        m.addField(new Field71A("OUR"));

        /*
         * Add the trailer block (in normal situations this is automatically created by the FIN interface, not by the user/applications)
         */
        SwiftBlock5 block5 = new SwiftBlock5();
        block5.append(new Tag("MAC", "00000000"));
        block5.append(new Tag("PDE", ""));
        m.getSwiftMessage().addBlock(block5);

        /*
         * Add an optional user block
         */
        SwiftBlockUser blockUser = new SwiftBlockUser("S");
        blockUser.append(new Tag("SAC", ""));
        blockUser.append(new Tag("COP", "P"));
        m.getSwiftMessage().addBlock(blockUser);

        /*
         * Create and print out the SWIFT FIN message string
         */
        System.out.println(m.message());
    }
}

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
import com.prowidesoftware.swift.model.field.Field;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field21A;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;

/**
 * This example shows how to read a proprietary SWIFT MT message from a String.
 * In the example we use an MT 798 with an inner MT 700.
 * <p>
 * Running this program produces the following output:
 * <pre>
 * Sender: PTSAUSDDXXXX
 * Receiver: PTSAUSDDXXXX
 * Sender's Reference: FOOI102794-02
 *
 * MT 700:
 * 27A: 2/2
 * 21A: AA2015-99
 * 27: 1/1
 * 40A: IRREVOCABLE
 * 20: FOOI102794
 * 31C: 150827
 *
 * MT 700:
 * Related Sequence Reference: AA2015-99
 * </pre>
 */
public class ParseProprietaryMessageMT798 {
    public static void main(String[] args) {
        /*
         * A simple String containing the message content to parse
         */
        String msg = "{1:F01PTSAUSDDXXXX0000000000}{2:I798PTSAUSDDXXXXU1005}{4:\n" +
                /*
                 * fields from MTn98
                 */
                ":20:FOOI102794-02\n" +
                ":12:700\n" +
                ":77E:\n" +
                /*
                 * fields from inner MT700
                 */
                ":27A:2/2\n" +
                ":21A:AA2015-99\n" +
                ":27:1/1\n" +
                ":40A:IRREVOCABLE\n" +
                ":20:FOOI102794\n" +
                ":31C:150827\n" +
                "-}";
        /*
         * Parse the String content into a SWIFT message object
         */
        MT798 mt = MT798.parse(msg);

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

        /*
         * Print fields from inner message.
         *
         * There will be no specific getters for these in the MT798 because
         * the inner message can have a variable structure. So we use the
         * generic API to retrieve the inner message block
         */
        SwiftTagListBlock block = mt.getSwiftMessage().getBlock4().getSubBlockAfterFirst("77E", false);

        /*
         * We iterate through all fields in the block
         */
        System.out.println("\nMT 700:");
        for (Tag t : block.getTags()) {
            System.out.println(t.getName() + ": " + t.getValue());
        }

        /*
         * Alternatively we can find specific fields in the inner block
         */
        System.out.println("\nMT 700:");
        Field21A ref = (Field21A) block.getFieldByName("21A");
        System.out.println(ref.getName() + ": " + ref.getComponent(Field21A.REFERENCE));
    }
}

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

import com.prowidesoftware.swift.model.SwiftMessage;
import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.field.Field;

import java.io.IOException;
import java.util.Locale;

/**
 * Example to generate a basic human friendly printout of an MT content
 *
 * <p>https://stackoverflow.com/questions/47265944/generating-human-readable-swift-messages-using-prowide-core
 */
public class MTHumanReadablePrintout {

    public static void main(String[] args) throws IOException {
        Locale locale = Locale.getDefault();
        SwiftMessage sm = SwiftMessage.parse("{1:F01BACOARB1A0B20000000000}{2:I103ADRBNL21XXXXU2}{3:{108:FOOB3926BE868XXX}}{4:\n" +
                ":20:REFERENCE\n" +
                ":23B:CRED\n" +
                ":32A:180730USD1234567,89\n" +
                ":50A:/12345678901234567890\n" +
                "CFIMHKH1XXX\n" +
                ":59:/12345678901234567890\n" +
                "JOE DOE\n" +
                "MyStreet 1234\n" +
                ":71A:OUR\n" +
                "-}{5:{CHK:3916EF336FF7}}");

        /*
         * With single value per field
         */
        System.out.println("Sender: " + sm.getSender());
        System.out.println("Receiver: " + sm.getReceiver() + "\n");

        for (Tag tag : sm.getBlock4().getTags()) {
            Field field = tag.asField();
            System.out.println(Field.getLabel(field.getName(), "103", null, locale));
            System.out.println(field.getValueDisplay(locale) + "\n");
        }

        /*
         * With values per component
         */
        for (Tag tag : sm.getBlock4().getTags()) {
            Field field = tag.asField();
            System.out.println("\n" + Field.getLabel(field.getName(), "103", null, locale));
            for (int component = 1; component <= field.componentsSize(); component++) {
                if (field.getComponent(component) != null) {
                    System.out.print(field.getComponentLabel(component) + ": ");
                    System.out.println(field.getValueDisplay(component, locale));
                }
            }
        }

    }
}

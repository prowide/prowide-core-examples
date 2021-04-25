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
import com.prowidesoftware.swift.model.field.Field32A;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.utils.Lib;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * This example shows how to read a SWIFT MT message from a file, in the context where
 * the message type to parse is already known, in the example we use an MT 103.
 * <p>
 * Running this program produces the following output:
 * <pre>
 * Sender: ESPBESMMAXXX
 * Receiver: BICFOOYYAXXX
 * Sender's Reference: 0061350113089908
 * Value Date: 2006/10/28
 * Amount: EUR 100000,
 * </pre>
 */
public class ParseMT103FromFileExample {

    public static void main(String[] args) throws IOException {
        /*
         * Read and parse the file content into a SWIFT message object
         * Parse from File could also be used here
         */
        MT103 mt = MT103.parse(Lib.readResource("mt103.txt", null));

        /*
         * Print header information
         */
        System.out.println("Sender: " + mt.getSender());
        System.out.println("Receiver: " + mt.getReceiver());

        /*
         * Print details of a specific fields
         */
        Field20 ref = mt.getField20();
        System.out.println(Field.getLabel(ref.getName(), mt.getMessageType(), null) + ": " + ref.getComponent(Field20.REFERENCE));

        Field32A f = mt.getField32A();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println("Value Date: " + sdf.format(f.getDateAsCalendar().getTime()));
        System.out.println("Amount: " + f.getCurrency() + " " + f.getAmount());
    }
}

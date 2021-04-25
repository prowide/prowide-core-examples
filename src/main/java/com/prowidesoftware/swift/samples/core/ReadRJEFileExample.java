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

import com.prowidesoftware.swift.io.RJEReader;
import com.prowidesoftware.swift.model.field.Field32A;
import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.utils.Lib;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * This example shows how to read a SWIFT MT message from an RJE file.
 * The example file used contains two MT103 messages.
 *
 * <p>The RJE reader is scheduled for release in version 7.8
 * In the meantime it is available on source code, or upon request.
 */
public class ReadRJEFileExample {

    public static void main(String[] args) throws IOException {
        /*
         * Read and parse the file content into a SWIFT message object
         * Parse from File could also be used here
         */
        RJEReader reader = new RJEReader(Lib.readResource("mt103.rje", null));

        /*
         * Iterate the reader
         */
        while (reader.hasNext()) {
            /*
             * Read the message.
             */
            //System.out.println(reader.next());
            AbstractMT msg = reader.nextMT();

            if (msg.isType(103)) {

                /*
                 * Specialize the message
                 */
                MT103 mt = (MT103) msg;

                /*
                 * Print some content from message
                 *
                 * Expected output:
                 * Sender: FOOTUS3NBXXX
                 * Receiver: BICFOOYYAXXX
                 * Reference: FDF0510141142100
                 * Value Date: 2005/10/14
                 * Amount: USD 1814,28
                 * --------------------------
                 * Sender: FOOBESMMAXXX
                 * Receiver: BICFOOYYAXXX
                 * Reference: INGDESMM
                 * Value Date: 2005/10/28
                 * Amount: EUR 111222,33
                 * --------------------------
                 */
                System.out.println("Sender: " + mt.getSender());
                System.out.println("Receiver: " + mt.getReceiver());
                System.out.println("Reference: " + mt.getField20().getValue());

                Field32A f = mt.getField32A();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                System.out.println("Value Date: " + sdf.format(f.getDateAsCalendar().getTime()));
                System.out.println("Amount: " + f.getCurrency() + " " + f.getAmount());
                System.out.println("--------------------------");
            }
        }
    }
}

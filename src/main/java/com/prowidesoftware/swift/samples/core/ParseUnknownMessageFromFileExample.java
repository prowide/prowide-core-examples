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
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.utils.Lib;

import java.io.IOException;

/**
 * This example shows how to read a SWIFT MT message from a file, in the context where
 * the message type to parse is unknown and also it can be a system message.
 * This example uses the generic parser instead of the AbstractMT class.
 */
public class ParseUnknownMessageFromFileExample {

    public static void main(String[] args) throws IOException {
        /*
         * Read the file and create an instance of the generic parser for it
         * Parse from File could also be used here
         */
        String content = Lib.readResource("system.txt");
        SwiftMessage msg = SwiftMessage.parse(content);

        if (msg.isServiceMessage()) {
            System.out.println("System Message");
            /*
             * deal with system message
             */
            Tag t = msg.getBlock4().getTagByName("177");
            if (t != null) {
                System.out.println(t.getValue());
            }

        } else {
            /*
             * specialize message as necessary depending on message type
             */
            if (msg.isType(103)) {
                /*
                 * Specialize the message to its specific model representation
                 */
                MT103 mt = new MT103(msg);

                /*
                 * Print details of a specific field
                 */
                System.out.println("Reference: " + mt.getField20().getValue());
            }
        }
    }
}

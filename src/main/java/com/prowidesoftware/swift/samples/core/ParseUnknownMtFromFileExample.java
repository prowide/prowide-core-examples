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

import com.prowidesoftware.swift.model.mt.AbstractMT;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103_STP;
import com.prowidesoftware.swift.utils.Lib;

import java.io.IOException;

/**
 * This example shows how to read a SWIFT MT message from a file, in the context where
 * the message type to parse is unknown.
 */
public class ParseUnknownMtFromFileExample {

    public static void main(String[] args) throws IOException {
        /*
         * Read and parse the file content into a SWIFT message object
         * Parse from File could also be used here
         */
        AbstractMT msg = AbstractMT.parse(Lib.readResource("mt103.txt", null));

        /*
         * Print header information
         */
        System.out.println("Sender: " + msg.getSender());
        System.out.println("Receiver: " + msg.getReceiver());
        System.out.println("MT: " + msg.getMessageType());

        if (msg.isType(103) && msg.getSwiftMessage().isSTP()) {
            /*
             * Specialize the message to its specific model representation
             */
            MT103_STP mt = (MT103_STP) msg;

            /*
             * Print details of a specific field
             */
            System.out.println("Reference: " + mt.getField20().getValue());
        }
    }
}

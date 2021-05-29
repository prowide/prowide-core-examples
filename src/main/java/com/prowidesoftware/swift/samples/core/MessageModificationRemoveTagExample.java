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

import com.prowidesoftware.swift.model.SwiftBlock4;
import com.prowidesoftware.swift.model.field.Field32A;
import com.prowidesoftware.swift.model.field.Field57A;
import com.prowidesoftware.swift.model.mt.mt1xx.MT103;
import com.prowidesoftware.swift.utils.Lib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This example shows how to remove a specific tag from a SWIFT MT message read from file.
 * <p>
 * Running this program will change current message content...
 * <pre>
 * :20:0061350113089908
 * :13C:/RNCTIME/1534+0000
 * :23B:CRED
 * :23E:SDVA
 * :32A:061028EUR100000,
 * :33B:EUR100000,
 * </pre>
 * into this:
 * <pre>
 * :20:0061350113089908
 * :13C:/RNCTIME/1534+0000
 * :23E:SDVA
 * :33B:EUR100000,
 * </pre>
 *
 * <p>For more information of the underlying generic message model check:
 * http://api.prowidesoftware.com/core/com/prowidesoftware/swift/model/SwiftMessage.html
 * </p>
 *
 * <p>For detailed available API to retrieve and alter content in the text block of MT messages, check:
 * http://api.prowidesoftware.com/core/com/prowidesoftware/swift/model/SwiftTagListBlock.html
 * </p>
 */
public class MessageModificationRemoveTagExample {

    public static void main(String[] args) throws IOException {
        /*
         * Read and parse the file content from resources into a SWIFT message object
         * Parse from File could also be used here
         */
        MT103 mt = MT103.parse(Lib.readResource("mt103.txt", null));

        /*
         * Print current message content
         */
        System.out.println("before\n:" + mt.message());

        /*
         * Notice the MT103 and its getFieldNN API are a facade
         * intended for parse/read, not for modification.
         *
         * To change values, the underlying SwiftMessage object
         * and its Tag objects must be use
         */
        SwiftBlock4 b4 = mt.getSwiftMessage().getBlock4();

        /*
         * Remove a specific tag by its name
         */
        b4.removeTag("32A");

        /*
         * Remove tag by index 2, which is actually the tag :23B:CRED
         */
        b4.getTags().remove(2);

        System.out.println("after\n:" + mt.message());
    }
}

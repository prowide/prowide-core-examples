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
import java.util.Calendar;

/**
 * This example shows how to modify specific content from a SWIFT MT message read from file.
 * <p>
 * Running this program will change current message content...
 * <pre>
 * :20:0061350113089908
 * :32A:061028EUR100000,
 * :57A:BICFOOYYXXX
 * </pre>
 * into this:
 * <pre>
 * :20:NEWREFERENCE
 * :32A:161104EUR100000,
 * :57A:/12345
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
public class MessageModificationExample {

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
         * Change the reference field by setting its complete value
         */
        b4.getTagByName("20").setValue("NEWREFERENCE");

        /*
         * Change field 57 with new content from new field
         */
        Field57A field57A = new Field57A();
        field57A.setAccount("12345");
        field57A.setBIC("NEWAESMMXXX");
        b4.getTagByName("57A").setValue(field57A.getValue());

        /*
         * Update just the value date component in field 32A
         * First we get current field and just change the date, notice the read
         * Field instance is a detached object, changing it does not modify
         * the actual message.
         * Then use the detached modified field to update the current Tag
         * value in the underlying message
         */
        Field32A field32A = mt.getField32A().setComponent1(Calendar.getInstance());
        b4.getTagByName("32A").setValue(field32A.getValue());

        System.out.println("after\n:" + mt.message());
    }
}

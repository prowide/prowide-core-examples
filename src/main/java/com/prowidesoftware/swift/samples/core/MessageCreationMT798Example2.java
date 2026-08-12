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

import com.prowidesoftware.swift.model.field.*;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;

/**
 * Example of how to create an MT798 envelope message with the submessage type created by adding the necessary fields.
 * <p>
 * Running this program produces the following SWIFT FIN message content:
 * <pre>
 * {1:F01AAAAUSXXAXXX0000000000}{2:I798BBBBUSXXXXXXN}{4:
 * :20:MNN9843
 * :12:745
 * :77E:
 * :27A:1/2
 * :21P:34567AC1-1239
 * :21S:34567AC1
 * :20:PGFFA0815999
 * :31C:200506
 * :13E:202005061045
 * :29B:Arthur Foo
 * -}
 * </pre>
 */
public class MessageCreationMT798Example2 {

    public static void main(String[] args) {
        // create the envelope message
        MT798 mt = new MT798("AAAAUSXXXXX", "BBBBUSXXXXX");

        // add mandatory fields from the envelope
        mt.append(new Field20("MNN9843"));
        mt.append(new Field12("745"));

        // add the separatory field
        mt.append(new Field77E());

        // add the submessage type (745) fields
        mt.append(new Field27A("1/2"));
        mt.append(new Field21P("34567AC1-1239"));
        mt.append(new Field21S("34567AC1"));
        mt.append(new Field20("PGFFA0815999"));
        mt.append(new Field31C("200506"));
        mt.append(new Field13E("202005061045"));
        mt.append(new Field29B("Arthur Foo"));

        /*
         * Print the content of the created MT798
         */
        System.out.println(mt.message());
    }

}

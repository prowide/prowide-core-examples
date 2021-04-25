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
import com.prowidesoftware.swift.model.field.Field20C;
import com.prowidesoftware.swift.model.field.Field23G;
import com.prowidesoftware.swift.model.mt.mt5xx.MT548;

/**
 * Example of message creation using a specific MTnnn class, the Sequences API and
 * nested subsequences.
 * A partial MT548 is created, including a sequence A with inner sequences A1 and A2.
 */
public class MessageCreationSequenceExampleMT548 {

    public static void main(String[] args) {
        MT548 mt = new MT548().append(MT548.SequenceA.newInstance(
                /* Full content of sequence A here
                 * We create a SwiftTagListBlock to append both field and subsequences */
                new SwiftTagListBlock()
                        .append(Field20C.emptyTag())
                        .append(Field23G.emptyTag())
                        .append(MT548.SequenceA1.newInstance(
                                // Sequence A1 contents here
                        ))
                        .append(MT548.SequenceA2.newInstance(
                                // Sequence A2 contents here
                        ))
        ));
        /*
         * Create and print out the SWIFT FIN message string
         */
        System.out.println(mt.message());
    }
}

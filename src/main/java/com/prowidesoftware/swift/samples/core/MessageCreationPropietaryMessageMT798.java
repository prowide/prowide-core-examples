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

import com.prowidesoftware.swift.model.field.Field12;
import com.prowidesoftware.swift.model.field.Field20;
import com.prowidesoftware.swift.model.field.Field21A;
import com.prowidesoftware.swift.model.field.Field27;
import com.prowidesoftware.swift.model.field.Field77E;
import com.prowidesoftware.swift.model.mt.mt7xx.MT700;
import com.prowidesoftware.swift.model.mt.mt7xx.MT798;

/**
 * Example of message creation using a specific MTnnn class and appenders to create a free format
 * message.
 * <p>
 * An MT798 is created and converted to the SWIFT FIN format.
 * <p>
 * Running this program produces the following SWIFT FIN message content:
 * <pre>
 * {1:F01FOOSEDR0AXXX0000000000}{2:I798FOORECV0XXXXN}{4:
 * :20:FOOI102794-02
 * :12:700
 * :77E:
 * :27A:2/2
 * :21A:FOOBAR
 * -}
 * </pre>
 */
public class MessageCreationPropietaryMessageMT798 {

  /**
   * This example creates a new MT798 using MT and Field helper classes.
   */
  public static void main(String[] args) {
    /*
     * Create the MT class, it will be initialized as an outgoing message with normal priority
     */
    final MT798 m = new MT798();

    /*
     * Set sender and receiver BIC codes
     */
    m.setSender("FOOSEDR0AXXX");
    m.setReceiver("FOORECV0XXXX");

    /*
     * Start adding the message's fields in correct order
     * This fields are part of the n98 specification
     */
    m.addField(new Field20("FOOI102794-02"));
    m.addField(new Field12("700"));
    m.addField(new Field77E(""));

    /*
     * Proprietary message goes here.
     *
     * This usually involves attaching the text block of another messages.
     * Fields can be individually appended here without restrictions, or
     * the complete text block may be added.
     */
    MT700 mt700 = new MT700();
    mt700.addField(new Field27("2/2"));
    mt700.addField(new Field21A("FOOBAR"));

    m.append(mt700.getSwiftMessage().getBlock4());

    /*
     * Create and print out the SWIFT FIN message string
     */
    System.out.println(m.message());
  }
}

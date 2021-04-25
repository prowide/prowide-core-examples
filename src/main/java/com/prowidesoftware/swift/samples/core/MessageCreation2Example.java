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
import com.prowidesoftware.swift.model.mt.mt5xx.MT542;
import com.prowidesoftware.swift.model.mt.mt5xx.MT542.SequenceA;

import java.util.Calendar;

/**
 * Example of message creation using a specific MTnnn class and the Sequences API.
 * An MT542 is created and converted to the SWIFT FIN format.
 * <p>
 * Running this program produces the following SWIFT FIN message content:
 *
 * <pre>
 * {1:F01FOOSEDR0AXXX0000000000}{2:I542FOORECV0XXXXN}{4:
 * :16R:GENL
 * :20C::SEME//2005071800000923
 * :23G:NEWM
 * :98A::PREP//20141031
 * :16S:GENL
 * :16R:TRADDET
 * :98A::TRAD//20050714
 * :98A::SETT//20050719
 * :90B::DEAL//ACTU/EUR21,49
 * :35B:ISIN FR1234567890
 * AXA UAP
 * :70E::SPRO//4042
 * :16S:TRADDET
 * :16R:FIAC
 * :36B::SETT//UNIT/200,00
 * :97A::SAFE//123456789
 * :16S:FIAC
 * :16R:SETDET
 * :22F::SETR//TRAD
 * :16R:SETPRTY
 * :95R::DEAG/SICV/4042
 * :16S:SETPRTY
 * :16R:SETPRTY
 * :95P::SELL//CITIFRPP
 * :97A::SAFE//123456789
 * :16S:SETPRTY
 * :16R:SETPRTY
 * :95P::PSET//SICVFRPP
 * :16S:SETPRTY
 * :16R:AMT
 * :19A::SETT//EUR123456,50
 * :16S:AMT
 * :16S:SETDET
 * :20:REFERENCE
 * :23B:CRED
 * -}
 * </pre>
 */
public class MessageCreation2Example {

    /**
     * This example creates a new MT103 using MT and Field helper classes.
     */
    public static void main(String[] args) {
        /*
         * Create the MT class, it will be initialized as an outgoing message with normal priority
         */
        final MT542 m = new MT542();

        /*
         * Set sender and receiver BIC codes
         */
        m.setSender("FOOSEDR0AXXX");
        m.setReceiver("FOORECV0XXXX");

        /*
         * Add a field using comprehensive setters API, will use it later inside sequence A
         */
        Field98A f98A = new Field98A()
                .setQualifier("PREP")
                .setDate(Calendar.getInstance());

        /*
         * Start adding the message's fields in correct order, starting with general information sequence
         */
        SequenceA A = MT542.SequenceA.newInstance(
                /*
                 * Add field using the complete literal value
                 */
                Field20C.tag(":SEME//2005071800000923"),
                Field23G.tag("NEWM"),
                f98A.asTag());
        /*
         * Add sequence A to message
         */
        m.append(A);

        /*
         * trade details sequence B
         */
        m.append(MT542.SequenceB.newInstance(
                Field98A.tag(":TRAD//20050714"),
                Field98A.tag(":SETT//20050719"),
                Field90B.tag(":DEAL//ACTU/EUR21,49"),
                new Field35B().setQualifier("ISIN").setISIN("FR1234567890").setDescription("AXA UAP").asTag(),
                Field70E.tag(":SPRO//4042")));

        /*
         * financial instrument account sequence C
         */
        m.append(MT542.SequenceC.newInstance(
                Field36B.tag(":SETT//UNIT/200,00"),
                Field97A.tag(":SAFE//123456789")));

        /*
         * settlement details: sequence E
         */
        // use constant of Tag that marks the start of sequence
        m.append(MT542.SequenceE.START_TAG);

        m.append(Field22F.tag(":SETR//TRAD"));

        m.append(MT542.SequenceE1.newInstance(Field95R.tag(":DEAG/SICV/4042")));

        m.append(MT542.SequenceE1.newInstance(Field95P.tag(":SELL//CITIFRPP"), Field97A.tag(":SAFE//123456789")));

        m.append(MT542.SequenceE1.newInstance(Field95P.tag(":PSET//SICVFRPP")));

        m.append(MT542.SequenceE3.newInstance(Field19A.tag(":SETT//EUR123456,50")));

        // use constant of Tag that marks the end of sequence
        m.append(MT542.SequenceE.END_TAG);

        m.append(new Field20("REFERENCE"));
        m.append(new Field23B("CRED"));

        /*
         * Create and print out the SWIFT FIN message string
         */
        System.out.println(m.message());
    }
}

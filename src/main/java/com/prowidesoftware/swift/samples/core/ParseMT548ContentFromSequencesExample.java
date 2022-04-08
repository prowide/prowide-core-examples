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
import com.prowidesoftware.swift.model.field.Field22H;
import com.prowidesoftware.swift.model.mt.mt5xx.MT548;

/**
 * Parses an MT548 into a model object and then uses the sequences API to get fields from a particular sequence.
 * You can use similar code to access any field within any sequence in any MT. If the sequence is repetitive you will
 * get a list of sequence instances to iterate.
 * <p>
 * Notice the Sequence models are just subclasses of the generic SwiftTagListBlock that provides plenty of options to
 * get fields, by name, value, etc.
 * <p>
 * Running this program produces the following output:
 * <pre>
 * REDE RECE
 * PAYM FREE
 * </pre>
 */
public class ParseMT548ContentFromSequencesExample {

    public static void main(String[] args) {
        String fin = "{1:F01AAAAUS33AXXX0000000000}{2:I548BBBBGB2LXGSTN}{3:{108:090106C1234567}}{4:\n" +
                ":16R:GENL\n" +
                ":20C::SEME//REFERENCE123\n" +
                ":23G:INST\n" +
                ":16R:LINK\n" +
                ":13A::LINK//534\n" +
                ":20C::RELA//FRTJ123DEL2\n" +
                ":16S:LINK\n" +
                ":16R:STAT\n" +
                ":25D::IPRC//CAND\n" +
                ":16S:STAT\n" +
                ":16S:GENL\n" +
                ":16R:SETTRAN\n" +
                ":35B:ISIN A1234567890\n" +
                "Descripcion\n" +
                ":36B::SETT//FAMT/1,\n" +
                ":97A::SAFE//54321\n" +
                ":22F::SETR//REPU\n" +
                ":22H::REDE//RECE\n" +
                ":22H::PAYM//FREE\n" +
                ":22F::STCO//EXER\n" +
                ":98A::SETT//20170812\n" +
                ":16S:SETTRAN\n" +
                "-}{5:{MAC:C9A8F8F9F9F9}}";

        MT548 mt = MT548.parse(fin);

        MT548.SequenceB sequenceB = mt.getSequenceB();
        for (Field field : sequenceB.getFieldsByName(Field22H.NAME)) {
            Field22H field22H = (Field22H) field;
            System.out.println(field22H.getQualifier() + " " + field22H.getIndicator());
        }
    }
}

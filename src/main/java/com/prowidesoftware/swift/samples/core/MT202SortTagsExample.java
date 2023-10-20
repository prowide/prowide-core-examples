/*
 * Copyright 2006-2023 Prowide
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

import com.prowidesoftware.swift.model.Tag;
import com.prowidesoftware.swift.model.mt.mt2xx.MT202;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * This example shows how to sort the tags in a message according to the specification structure.
 * The {@code mt202Structure} is populated using the Integrator SDK as follows:
 * <pre>
 *   List<String> fieldsDesc = new ArrayList<>();
 *    requireNonNull(MtType.MT202.scheme()).getAllFields().forEach(schemeField -> {
 *        String currentField = schemeField.getName()
 *                + ";" + (schemeField.getMaxRepetitions() == -1 ? "R" : "_")
 *                + ";" + ofNullable(schemeField.getLetterOptionsString()).orElse("");
 *        fieldsDesc.add(currentField);
 *    });
 *    fieldsDesc.forEach(System.out::println);
 * </pre>
 */
public class MT202SortTagsExample {

    static List<String> mt202Structure = Arrays.asList(
            "20;_;",
            "21;_;",
            "13;R;C",
            "32;_;A",
            "52;_;A,D",
            "53;_;A,B,D",
            "54;_;A,B,D",
            "56;_;A,D",
            "57;_;A,B,D",
            "58;_;A,D",
            "72;_;"
    );

    static String unsortedFin =
            "{1:F01BWJWDKD0AXXX0000000000}{2:I202CATAHKHXXXXXN}{3:{121:0911115d-2828-4ed6-8d0a-074f67027c52}}{4:\r\n"
                    + ":20:5931700\r\n"
                    + ":53B:/1120682047\r\n"
                    + ":21:XXTMZZ\r\n"
                    + ":32A:991018GBP7869455,57\r\n"
                    + ":13C:/1120682047\r\n"
                    + ":52A:INVOGB2LXXX\r\n"
                    + "MACLGB33\r\n"
                    + ":13C:/1120682048\r\n"
                    + ":57A://SC123488\r\n"
                    + ":58A:/GB75MIDL40538800980543\r\n"
                    + "MACLGB33\r\n"
                    + "-}";

    static String expectedFin =
            "{1:F01BWJWDKD0AXXX0000000000}{2:I202CATAHKHXXXXXN}{3:{121:0911115d-2828-4ed6-8d0a-074f67027c52}}{4:\r\n"
                    + ":20:5931700\r\n"
                    + ":21:XXTMZZ\r\n"
                    + ":13C:/1120682047\r\n"
                    + ":13C:/1120682048\r\n"
                    + ":32A:991018GBP7869455,57\r\n"
                    + ":52A:INVOGB2LXXX\r\n"
                    + "MACLGB33\r\n"
                    + ":53B:/1120682047\r\n"
                    + ":57A://SC123488\r\n"
                    + ":58A:/GB75MIDL40538800980543\r\n"
                    + "MACLGB33\r\n"
                    + "-}";


    public static void main(String[] args) {
        MT202 mt202 = MT202.parse(unsortedFin);
        //System.out.println("UNSORTED:" + mt202.message());

        List<Tag> tags = new ArrayList<>(mt202.getSwiftMessage().getBlock4().getTags());
        List<Tag> sortedTags = sortTags(tags);
        mt202.getSwiftMessage().getBlock4().setTags(sortedTags);

        //System.out.println("SORTED:" + mt202.message());
        //System.out.println("EXPECTED:" + expectedFin);
    }

    private static List<Tag> sortTags(List<Tag> tags) {
        List<Tag> sortedTags = new ArrayList<>();
        for (String fieldDesc : mt202Structure) {
            String[] parts = fieldDesc.split(";");
            String fieldName = parts[0];
            boolean isRepetitiveField = "R".equals(parts[1]);
            String[] fieldLetterOptions = (parts.length > 2) ? parts[2].split(",") : null;

            Tag currentTag;
            do {
                currentTag = getTag(tags, fieldName, fieldLetterOptions);
                if (currentTag != null) sortedTags.add(currentTag);
            } while (isRepetitiveField && currentTag != null);
        }
        return sortedTags;
    }

    private static Tag getTag(List<Tag> tags, String fieldName, String[] fieldLetterOptions) {
        if (fieldLetterOptions != null) {
            for (String letterOpt : fieldLetterOptions) {
                Optional<Tag> tagOpt = tags.stream().filter(tag -> tag.getName().equals(fieldName + letterOpt)).findFirst();
                if (tagOpt.isPresent()) {
                    tags.remove(tagOpt.get());
                    return tagOpt.get();
                }
            }
        } else {
            Optional<Tag> tagOpt = tags.stream().filter(tag -> tag.getName().equals(fieldName)).findFirst();
            if (tagOpt.isPresent()) {
                tags.remove(tagOpt.get());
                return tagOpt.get();
            }
        }
        return null;
    }

}



/**
 * Copyright 2013 Nitor Creations Oy
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
package com.nitorcreations.robotframework.eclipseide.internal.assistant.proposalgenerator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.text.IRegion;
import org.eclipse.swt.graphics.Image;

import com.nitorcreations.robotframework.eclipseide.builder.parser.util.ParserUtil;

public class TableAttemptVisitor implements AttemptVisitor {
    private final Map<String, String> tableNameToFull = new LinkedHashMap<String, String>();

    public TableAttemptVisitor() {
        tableNameToFull.put("variables", "* Variables");
        tableNameToFull.put("settings", "* Settings");
        tableNameToFull.put("metadata", "* Settings");
        tableNameToFull.put("testcases", "* Test Cases");
        tableNameToFull.put("keywords", "* Keywords");
        tableNameToFull.put("userkeywords", "* Keywords");
    }

    @Override
    public RobotCompletionProposalSet visitAttempt(String attempt, IRegion replacementRegion) {
        assert attempt.equals(attempt.toLowerCase());
        Map<String, RobotCompletionProposal> ourProposals = new LinkedHashMap<String, RobotCompletionProposal>();
        String tableArgument = ParserUtil.parseTable(attempt);
        for (Entry<String, String> e : tableNameToFull.entrySet()) {
            if (e.getKey().startsWith(tableArgument)) {
                String proposal = e.getValue();
                Image image = null;
                String displayString = e.getValue();
                String additionalProposalInfo = null;
                String informationDisplayString = null;
                RobotCompletionProposal rcp = new RobotCompletionProposal(proposal, null, replacementRegion, image, displayString, informationDisplayString, additionalProposalInfo);
                rcp.setCursorPositionAdjustment(1);
                ourProposals.put(e.getValue(), rcp);
            }
        }
        RobotCompletionProposalSet ourProposalSet = new RobotCompletionProposalSet();
        ourProposalSet.getProposals().addAll(ourProposals.values());
        return ourProposalSet;
    }
}

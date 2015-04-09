/*******************************************************************************
 * Copyright (c) 2015, 2015 Bruno Medeiros and other Contributors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Bruno Medeiros - initial API and implementation
 *******************************************************************************/
package com.github.rustdt.ide.core.text;

import melnorme.lang.ide.core.TextSettings_Actual.LangPartitionTypes;
import melnorme.lang.ide.core.text.PatternRule_Fixed;
import melnorme.lang.ide.core.text.RuleBasedPartitionScannerExt;
import melnorme.utilbox.collections.ArrayList2;

import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class RustPartitionScanner extends RuleBasedPartitionScannerExt {
	
	public RustPartitionScanner() {
	}
	
	@Override
	protected void addRules(ArrayList2<IPredicateRule> rules) {
		addStandardRules(rules, 
			LangPartitionTypes.LINE_COMMENT.getId(), 
			LangPartitionTypes.BLOCK_COMMENT.getId(),
			LangPartitionTypes.DOC_LINE_COMMENT.getId(),
			LangPartitionTypes.DOC_BLOCK_COMMENT.getId(),
			null
		);
		
		IToken tkAttribute = new Token(LangPartitionTypes.ATTRIBUTE.getId());
		rules.add(new PatternRule_Fixed("#[", "]", tkAttribute, NO_ESCAPE_CHAR, false, true));
		rules.add(new PatternRule_Fixed("#![", "]", tkAttribute, NO_ESCAPE_CHAR, false, true));
		
		IToken tkCharacter = new Token(LangPartitionTypes.CHARACTER.getId());
		rules.add(new PatternRule_Fixed("'", "'", tkCharacter, '\\', true, true));
		rules.add(new PatternRule_Fixed("b'", "'", tkCharacter, '\\', true, true));
		
		IToken tkString = new Token(LangPartitionTypes.STRING.getId());
		rules.add(new PatternRule_Fixed("\"", "\"", tkString, '\\', false, true));
		rules.add(new PatternRule_Fixed("b\"", "\"", tkString, '\\', false, true));
		// Raw strings have different terminate, so they need to a different partition type.
		IToken tkRawString = new Token(LangPartitionTypes.RAW_STRING.getId());
		rules.add(new PatternRule_Fixed("r##\"", "\"##", tkRawString, NO_ESCAPE_CHAR, false, true));
		rules.add(new PatternRule_Fixed("br##\"", "\"##", tkRawString, NO_ESCAPE_CHAR, false, true));
		
	}
	
}
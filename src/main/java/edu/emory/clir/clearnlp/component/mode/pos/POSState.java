/**
 * Copyright 2014, Emory University
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
package edu.emory.clir.clearnlp.component.mode.pos;

import java.util.Map;
import java.util.Set;

import edu.emory.clir.clearnlp.component.CFlag;
import edu.emory.clir.clearnlp.component.state.AbstractLRState;
import edu.emory.clir.clearnlp.dependency.DEPNode;
import edu.emory.clir.clearnlp.dependency.DEPTree;

/**
 * @since 3.0.0
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class POSState extends AbstractLRState
{
	private Set<String> s_lowerSimplifiedWordForms;
	private Map<String,String> m_ambiguityClasses;
	
//	====================================== INITIALIZATION ======================================
	
	public POSState(DEPTree tree, CFlag flag, Set<String> lowerSimplifiedWordForms, Map<String,String> ambiguityClasses)
	{
		super(tree, flag);
		init(lowerSimplifiedWordForms, ambiguityClasses);
	}
	
	private void init(Set<String> lowerSimplifiedWordForms, Map<String,String> ambiguityClasses)
	{
		s_lowerSimplifiedWordForms = lowerSimplifiedWordForms;
		m_ambiguityClasses = ambiguityClasses;
	}
	
//	====================================== ORACLE/LABEL ======================================
	
	@Override
	protected String clearOracle(DEPNode node)
	{
		return node.clearPOSTag();
	}

//	====================================== TRANSITION ======================================
	
	protected void setLabel(DEPNode node, String label)
	{
		node.setPOSTag(label);
	}
	
//	====================================== HELPER ======================================
	
	public String getAmbiguityClass(DEPNode node)
	{
		return m_ambiguityClasses.get(node.getSimplifiedWordForm());
	}
	
	public boolean extractWordFormFeature(DEPNode node)
	{
		return s_lowerSimplifiedWordForms.contains(node.getLowerSimplifiedWordForm());
	}
}
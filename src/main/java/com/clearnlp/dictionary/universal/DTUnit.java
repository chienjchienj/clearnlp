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
package com.clearnlp.dictionary.universal;

import java.io.InputStream;
import java.util.Set;

import com.clearnlp.dictionary.AbstractDTTokenizer;
import com.clearnlp.dictionary.DTPath;
import com.clearnlp.util.CharUtils;
import com.clearnlp.util.DSUtils;
import com.clearnlp.util.IOUtils;


/**
 * @since 3.0.0
 * @author Jinho D. Choi ({@code jdchoi77@gmail.com})
 */
public class DTUnit extends AbstractDTTokenizer
{
	private Set<String> s_unit;
	
	public DTUnit()
	{
		init(IOUtils.getInputStreamsFromClasspath(DTPath.UNITS));
	}
	
	public DTUnit(InputStream in)
	{
		init(in);
	}
	
	public void init(InputStream in)
	{
		s_unit = DSUtils.createStringHashSet(in, true, true);
	}
	
	public boolean isUnit(String lower)
	{
		return s_unit.contains(lower);
	}

	@Override
	/** @return "1mg" -> {"1", "mg"}. */
	public String[] tokenize(String original, String lower, char[] lcs)
	{
		int len = original.length();
		
		for (String unit : s_unit)
		{
			if (lower.endsWith(unit))
			{
				int i = len - unit.length();
				
				if (0 < i && CharUtils.isDigit(lcs[i-1]))
					return new String[]{original.substring(0,i), original.substring(i)};
			}
		}
		
		return null;
	}
}
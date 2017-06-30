package com.chenlb.mmseg4j.analysis;

import java.io.File;

import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;

import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MaxWordSeg;
import com.chenlb.mmseg4j.Seg;


/**
 * 最多分词方式.
 * 
 * @author chenlb 2009-4-6 下午08:43:46
 */
public class MyAnalyzer2 extends MMSegAnalyzer {

	public MyAnalyzer2() {
		super();
	}

	public MyAnalyzer2(String path) {
		super(path);
	}

	public MyAnalyzer2(Dictionary dic) {
		super(dic);
	}

	public MyAnalyzer2(File path) {
		super(path);
	}

	protected Seg newSeg() {
		return new MaxWordSeg(dic);
	}
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		return new TokenStreamComponents(new MyTokenizer2(newSeg()));
	}
}

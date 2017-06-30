package com.chenlb.mmseg4j.analysis;

import java.io.File;

import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MaxWordSeg;
import com.chenlb.mmseg4j.Seg;


/**
 * 按字符单个分词，不过滤特殊字符、标点符合、空格等
 * 
 * @author chenlb 2009-4-6 下午08:43:46
 */
public class MyAnalyzer1 extends MMSegAnalyzer {

	public MyAnalyzer1() {
		super();
	}

	public MyAnalyzer1(String path) {
		super(path);
	}

	public MyAnalyzer1(Dictionary dic) {
		super(dic);
	}

	public MyAnalyzer1(File path) {
		super(path);
	}

	protected Seg newSeg() {
		return new MaxWordSeg(dic);
	}
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		return new TokenStreamComponents(new MyTokenizer1(newSeg()));
	}
}

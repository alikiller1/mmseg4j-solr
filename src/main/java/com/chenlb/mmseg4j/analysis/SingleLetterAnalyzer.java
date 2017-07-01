package com.chenlb.mmseg4j.analysis;

import java.io.File;

import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MaxWordSeg;
import com.chenlb.mmseg4j.Seg;


/**
 * 按字符单个分词，不过滤特殊字符、标点符合、空格等
 * @author LL
 *
 */
public class SingleLetterAnalyzer extends MMSegAnalyzer {

	public SingleLetterAnalyzer() {
		super();
	}

	public SingleLetterAnalyzer(String path) {
		super(path);
	}

	public SingleLetterAnalyzer(Dictionary dic) {
		super(dic);
	}

	public SingleLetterAnalyzer(File path) {
		super(path);
	}

	protected Seg newSeg() {
		return new MaxWordSeg(dic);
	}
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		return new TokenStreamComponents(new SingleLetterTokenizer(newSeg()));
	}
}

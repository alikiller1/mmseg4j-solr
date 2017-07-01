package com.chenlb.mmseg4j.analysis;

import java.io.IOException;

import com.chenlb.mmseg4j.Seg;
import com.chenlb.mmseg4j.Word;

/**
 * 按字符单个分词，过滤特殊字符、标点符合、空格等
 * @return
 * @throws IOException
 */
public class SingleWordTokenizer extends MMSegTokenizer {

	
	public SingleWordTokenizer(Seg seg) {
		super(seg);
	}
	@Override
	public final boolean incrementToken() throws IOException {
		clearAttributes();
		Word word = mmSeg.next2();
		if(word != null) {
			termAtt.copyBuffer(word.getSen(), word.getWordOffset(), word.getLength());
			offsetAtt.setOffset(word.getStartOffset(), word.getEndOffset());
			typeAtt.setType(word.getType());
			return true;
		} else {
			return false;
		}
	}
}

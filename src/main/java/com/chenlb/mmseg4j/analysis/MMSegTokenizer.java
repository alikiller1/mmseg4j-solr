package com.chenlb.mmseg4j.analysis;

import java.io.IOException;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;

import com.chenlb.mmseg4j.MMSeg;
import com.chenlb.mmseg4j.Seg;
import com.chenlb.mmseg4j.Word;

public class MMSegTokenizer extends Tokenizer {

	public MMSeg mmSeg;

	public CharTermAttribute termAtt;
	public OffsetAttribute offsetAtt;
	public TypeAttribute typeAtt;

	public final Seg seg;

	public MMSegTokenizer(Seg seg) {
		this.seg = seg;

		termAtt = addAttribute(CharTermAttribute.class);
		offsetAtt = addAttribute(OffsetAttribute.class);
		typeAtt = addAttribute(TypeAttribute.class);
	}

	public void reset() throws IOException {
		super.reset();
		//lucene 4.0
		//org.apache.lucene.analysis.Tokenizer.setReader(Reader)
		//setReader 自动被调用, input 自动被设置。
		if(mmSeg == null) {
			mmSeg = new MMSeg(input, seg);
		} else {
			mmSeg.reset(input);
		}
	}

	@Override
	public  boolean incrementToken() throws IOException {
		clearAttributes();
		Word word = mmSeg.next();
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

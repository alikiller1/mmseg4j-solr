package com.chenlb.mmseg4j.solr;

import com.chenlb.mmseg4j.*;
import com.chenlb.mmseg4j.analysis.MMSegTokenizer;
import com.chenlb.mmseg4j.analysis.MyTokenizer1;
import com.chenlb.mmseg4j.analysis.MyTokenizer2;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoaderAware;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class MyTokenizerFactory extends TokenizerFactory implements ResourceLoaderAware {

	private static final Logger logger = LoggerFactory.getLogger(MyTokenizerFactory.class);
	/* 线程内共享 */
	private ThreadLocal<MMSegTokenizer> tokenizerLocal = new ThreadLocal<MMSegTokenizer>();
	// protected dic for test
	protected Dictionary dic = null;

	public MyTokenizerFactory(Map<String, String> args) {
		super(args);
	}


	@Override
	public Tokenizer create(AttributeFactory factory) {
		MMSegTokenizer tokenizer = tokenizerLocal.get();
		if(tokenizer == null) {
			tokenizer = newTokenizer();
		}

		return tokenizer;
	}

	private MMSegTokenizer newTokenizer() {
		MMSegTokenizer tokenizer = createTokenizer(getOriginalArgs());
		tokenizerLocal.set(tokenizer);
		return tokenizer;
	}

	private MMSegTokenizer createTokenizer(Map<String, String> args) {
		MMSegTokenizer tokenizer = null;
		logger.info("create new tokenizer ...");
		
		String mode = args.get("mode");
		if("my1".equals(mode)) {
			//按字符单个分词，不过滤特殊字符、标点符合、空格等
			logger.info("use my1 mode");
			tokenizer = new MyTokenizer1(new SimpleSeg(dic));
		} else if("my2".equals(mode)) {
			//按字符单个分词，过滤特殊字符、标点符合、空格等
			logger.info("use my2 mode");
			tokenizer = new MyTokenizer2(new SimpleSeg(dic));
		} else {
			logger.info("default my2 mode");
			tokenizer = new MyTokenizer2(new SimpleSeg(dic));
		}
		return tokenizer;
	}

	@Override
	public void inform(ResourceLoader loader) {
		String dicPath = getOriginalArgs().get("dicPath");

		dic = Utils.getDict(dicPath, loader);

		logger.info("dic load... in={}", dic.getDicPath().toURI());
	}

}

package com.example.demo.soft.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.example.demo.soft.ShinseiXmlHelper;
import com.example.demo.soft.ZipCreate;
import com.example.demo.soft.entity.Mishikko;
import com.example.demo.soft.repository.MishikkoRepository;

@Service
public class MishikkoService {

	@Autowired
	MishikkoRepository repository;

	public Page<Mishikko> getAll(Pageable pageable){
		return repository.findAllByOrderByIdDesc(pageable);
	}

	public Mishikko find(Integer id) {
		return repository.findById(id).orElse(new Mishikko());
	}

	public Mishikko saveMishikko(Mishikko mishikko) {
		return repository.saveAndFlush(mishikko);
	}

	public String xmlFileGet(String shinseiName, Mishikko mishikko) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		// 1. DocumentBuilderFactoryのインスタンスを取得する
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// 2. DocumentBuilderのインスタンスを取得する
		DocumentBuilder builder = factory.newDocumentBuilder();

		// 3. DocumentBuilderにXMLを読み込ませ、Documentを作る
		List<Document> docList = ShinseiXmlHelper.getDocument(builder, shinseiName);


		// HM000000000.xml Element 取得
		Element mishikkoElement = ShinseiXmlHelper.xmlElement(docList);

		//info.xml element取得
		Element mishikkoInfoElement = ShinseiXmlHelper.infoElement(docList);

		//export.xml element取得
		Element mishikkoExportElement = ShinseiXmlHelper.exportElement(docList);

		ShinseiXmlHelper.elementTextset(mishikkoElement, "年月日", mishikko.getDate(),0);
		ShinseiXmlHelper.elementTextset(mishikkoElement, "宛先登記所名", mishikko.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(mishikkoElement, "提出先名称", mishikko.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(mishikkoElement, "登記所コード", mishikko.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(mishikkoElement, "提出先コード", mishikko.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(mishikkoInfoElement, "Kemmei", mishikko.getKenmei(), 0);
		ShinseiXmlHelper.elementTextset(mishikkoInfoElement, "ShinseisakiTokishoCode", mishikko.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(mishikkoInfoElement, "ShinseisakiTokishoMeisho", mishikko.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(mishikkoInfoElement, "SaishuKoshinNichiji", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), 0);
		ShinseiXmlHelper.elementTextset(mishikkoExportElement, "ファイル名", mishikko.getKenmei() + ".zip", 0);

		//物件追加の場合
		for(int i = 1; i < mishikko.getTaisyoBukkenList().size(); i++) {
			ShinseiXmlHelper.elementAdd(mishikkoElement, "対象物件情報", "問合書情報");
		}

		int bukkenCount = mishikkoElement.getElementsByTagName("対象物件情報").getLength();

		for(int i = 0; i < bukkenCount; i++) {
			ShinseiXmlHelper.elementTextset(mishikkoElement, "物件区分", mishikko.getTaisyoBukkenList().get(i).getBukkenKubun(), i);
			ShinseiXmlHelper.elementTextset(mishikkoElement, "地番区域情報", mishikko.getTaisyoBukkenList().get(i).getChibanKuiki(), i);
			ShinseiXmlHelper.elementTextset(mishikkoElement, "地番家屋番号情報", mishikko.getTaisyoBukkenList().get(i).getChibanKaokuBango(), i);
			ShinseiXmlHelper.elementTextset(mishikkoElement, "登記の目的", mishikko.getTaisyoBukkenList().get(i).getMokuteki(), i);
			ShinseiXmlHelper.elementTextset(mishikkoElement, "用紙区分", mishikko.getTaisyoBukkenList().get(i).getYoshiKubun(), i);
			ShinseiXmlHelper.elementTextset(mishikkoElement, "年月日", mishikko.getTaisyoBukkenList().get(i).getUketsukeDate(), i+1);
			ShinseiXmlHelper.elementTextset(mishikkoElement, "本番", mishikko.getTaisyoBukkenList().get(i).getUketsukeBango().toString(), i);
			ShinseiXmlHelper.elementTextset(mishikkoElement, "同順位符号", mishikko.getTaisyoBukkenList().get(i).getDojyuni(), i);
		}

		ShinseiXmlHelper.makeFolder(shinseiName);

		ShinseiXmlHelper.makeShinseiFile(docList, shinseiName);


        // ZIP化実施フォルダを取得
     	File destination = new File("./統合/" + mishikko.getKenmei() + ".zip");
     	File dirzip = new File("./export");

     	// 圧縮実行
     	ZipCreate.compressDirectory(destination, dirzip);

     	// ZIP化実施フォルダを取得
     	File dir = new File("./統合");

     	// ZIP化実施フォルダを取得
     	File destination2 = new File("./" + mishikko.getKenmei() + ".zip");

     	// 圧縮実行
     	ZipCreate.compressDirectory(destination2, dir);

		return "未失効照会外部ファイルを作成しました。";
	}
}

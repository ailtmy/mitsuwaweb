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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.example.demo.entity.customer.Person;
import com.example.demo.soft.ShinseiXmlHelper;
import com.example.demo.soft.ZipCreate;
import com.example.demo.soft.entity.Hozon;
import com.example.demo.soft.entity.Kenrisya;
import com.example.demo.soft.repository.HozonRepository;

@Service
public class HozonService {

	@Autowired
	HozonRepository repository;

	public Page<Hozon> getAll(Pageable pageable){
		return repository.findAllByOrderByIdDesc(pageable);
	}

	public Hozon find(Integer id) {
		return repository.findById(id).orElse(new Hozon());
	}

	public Hozon saveHozon(Hozon hozon) {
		return repository.saveAndFlush(hozon);
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public String xmlFileGet(String shinseiName, Hozon hozon) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		// 1. DocumentBuilderFactoryのインスタンスを取得する
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// 2. DocumentBuilderのインスタンスを取得する
		DocumentBuilder builder = factory.newDocumentBuilder();

		// 3. DocumentBuilderにXMLを読み込ませ、Documentを作る
		List<Document> docList = ShinseiXmlHelper.getDocument(builder, shinseiName);

		// HM000000000.xml Element 取得
		Element hozonElement = ShinseiXmlHelper.xmlElement(docList);

		//info.xml element取得
		Element hozonInfoElement = ShinseiXmlHelper.infoElement(docList);

		//export.xml element取得
		Element hozonExportElement = ShinseiXmlHelper.exportElement(docList);

		ShinseiXmlHelper.elementTextset(hozonElement, "原因", hozon.getGenin(),0);
		//所有者セット
		Element syoyusyaElement = docList.get(0).createElement("所有者");
		NodeList shinseijiko = docList.get(0).getElementsByTagName("申請事項");
		shinseijiko.item(0).appendChild(syoyusyaElement);
		for(Kenrisya kenrisya : hozon.getSyoyusya()) {
			Element meigininElement = docList.get(0).createElement("名義人情報");
			syoyusyaElement.appendChild(meigininElement);

			Element addrElement = docList.get(0).createElement("住");
			addrElement.setTextContent(kenrisya.getAddr());
			meigininElement.appendChild(addrElement);

			if(kenrisya.getMochibun().isEmpty() || kenrisya.getMochibun() == null) {

			} else {
				Element mochiElement = docList.get(0).createElement("持");
				mochiElement.setTextContent(kenrisya.getMochibun());
				meigininElement.appendChild(mochiElement);
				}
			Element nameElement = docList.get(0).createElement("氏");
			nameElement.setTextContent(kenrisya.getCustomer().getName());
			meigininElement.appendChild(nameElement);

			if(kenrisya.getCustomer() instanceof Person) {

			} else {
				Element daitoriElement = docList.get(0).createElement("代");
				meigininElement.appendChild(daitoriElement);
				Element nyuryokuhouhouElement = docList.get(0).createElement("入力方法");
				nyuryokuhouhouElement.setTextContent("その他");
				daitoriElement.appendChild(nyuryokuhouhouElement);

				Element shikakuElement = docList.get(0).createElement("資格");
				shikakuElement.setTextContent(kenrisya.getDaihyo().split("/")[0]);
				daitoriElement.appendChild(shikakuElement);

				Element daihyosyashimeiElement = docList.get(0).createElement("代表者氏名");
				daihyosyashimeiElement.setTextContent(kenrisya.getDaihyo().split("/")[1]);
				daitoriElement.appendChild(daihyosyashimeiElement);
			}

			Element shikibetsuhakkoElement = docList.get(0).createElement("識別情報発行区分");
			shikibetsuhakkoElement.setTextContent("送付の方法による交付を希望する");
			meigininElement.appendChild(shikibetsuhakkoElement);

		}

		String tempsyorui = "";
		for(String syorui : hozon.getTempsyorui().getSyoruis()) {
			tempsyorui += syorui + "\n";
		}
		ShinseiXmlHelper.elementTextset(hozonElement, "添付情報", tempsyorui, 0);

		ShinseiXmlHelper.elementTextset(hozonElement, "申請年月日", hozon.getDate() + "　法７４条２項",0);
		ShinseiXmlHelper.elementTextset(hozonElement, "宛先登記所名", hozon.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(hozonElement, "提出先名称", hozon.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(hozonElement, "登記所コード", hozon.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(hozonElement, "提出先コード", hozon.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(hozonElement, "課税価格合計額", String.valueOf(hozon.getKazeiGoukei()), 0);
		ShinseiXmlHelper.elementTextset(hozonElement, "課税価格の内訳等", hozon.getKazeiUchiwake(), 0);
		ShinseiXmlHelper.elementTextset(hozonElement, "登録免許税合計額", String.valueOf(hozon.getToumenGoukei()), 0);
		ShinseiXmlHelper.elementTextset(hozonElement, "登録免許税内訳等", hozon.getToumenUchiwake(), 0);
		ShinseiXmlHelper.elementTextset(hozonElement, "登録免許税適用条項", hozon.getJyobun(), 0);

		ShinseiXmlHelper.elementTextset(hozonInfoElement, "Kemmei", hozon.getKenmei(), 0);
		ShinseiXmlHelper.elementTextset(hozonInfoElement, "ShinseisakiTokishoCode", hozon.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(hozonInfoElement, "ShinseisakiTokishoMeisho", hozon.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(hozonInfoElement, "SaishuKoshinNichiji", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), 0);
		ShinseiXmlHelper.elementTextset(hozonExportElement, "ファイル名", hozon.getKenmei() + ".zip", 0);

		//物件セット
		ShinseiXmlHelper.makeShinseiBukken(docList.get(0), hozon);

		ShinseiXmlHelper.makeFolder(shinseiName, hozon.getKenmei());

		ShinseiXmlHelper.makeShinseiFile(docList, shinseiName, hozon.getKenmei());


        // ZIP化実施フォルダを取得
     	File destination = new File("./" + hozon.getKenmei() + "s/" + hozon.getKenmei() + ".zip");
     	File dirzip = new File("./" + hozon.getKenmei());

     	// 圧縮実行
     	ZipCreate.compressDirectory(destination, dirzip);

     	// ZIP化実施フォルダを取得
     	File dir = new File("./" + hozon.getKenmei() + "s");

     	// ZIP化実施フォルダを取得
     	File destination2 = new File("./" + hozon.getKenmei() + ".zip");

     	// 圧縮実行
     	ZipCreate.compressDirectory(destination2, dir);

		return "２項保存外部ファイルを作成しました。";
	}


}

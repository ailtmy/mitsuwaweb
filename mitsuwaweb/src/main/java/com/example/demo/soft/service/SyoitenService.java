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

import com.example.demo.entity.customer.Company;
import com.example.demo.entity.customer.Person;
import com.example.demo.soft.ShinseiXmlHelper;
import com.example.demo.soft.ZipCreate;
import com.example.demo.soft.entity.Gimusya;
import com.example.demo.soft.entity.Kenrisya;
import com.example.demo.soft.entity.Syoiten;
import com.example.demo.soft.repository.SyoitenRepository;

@Service
public class SyoitenService {

	@Autowired
	SyoitenRepository repository;

	public Page<Syoiten> getAll(Pageable pageable){
		return repository.findAllByOrderByIdDesc(pageable);
	}

	public Syoiten find(Integer id) {
		return repository.findById(id).orElse(new Syoiten());
	}

	public Syoiten saveSyoiten(Syoiten syoiten) {
		return repository.saveAndFlush(syoiten);
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public String xmlFileGet(String shinseiName, Syoiten syoiten)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {

		// 1. DocumentBuilderFactoryのインスタンスを取得する
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// 2. DocumentBuilderのインスタンスを取得する
		DocumentBuilder builder = factory.newDocumentBuilder();

		// 3. DocumentBuilderにXMLを読み込ませ、Documentを作る
		List<Document> docList = ShinseiXmlHelper.getDocument(builder, shinseiName);

		// HM000000000.xml Element 取得
		Element syoitenElement = ShinseiXmlHelper.xmlElement(docList);

		//info.xml element取得
		Element syoitenInfoElement = ShinseiXmlHelper.infoElement(docList);

		//export.xml element取得
		Element syoitenExportElement = ShinseiXmlHelper.exportElement(docList);


		//申請書組み立て
		ShinseiXmlHelper.elementTextset(syoitenElement, "登記の目的", syoiten.getMokuteki(), 0);
		ShinseiXmlHelper.elementTextset(syoitenElement, "原因", syoiten.getGenin(), 0);

		NodeList shinseijiko = docList.get(0).getElementsByTagName("申請事項");

		//権利者セット
		Element kenrisyaElement = docList.get(0).createElement("権利者");
		shinseijiko.item(0).appendChild(kenrisyaElement);
		for(Kenrisya kenrisya : syoiten.getKenrisyaList()) {
			Element meigininElement = docList.get(0).createElement("名義人情報");
			kenrisyaElement.appendChild(meigininElement);

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
				Element shitenElement = docList.get(0).createElement("取");
				meigininElement.appendChild(shitenElement);

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

				Company company = (Company) kenrisya.getCustomer();
				Element kaisyahoujinnbangoElement = docList.get(0).createElement("会社法人等番号");
				kaisyahoujinnbangoElement.setTextContent(company.getHoujinbango());
				meigininElement.appendChild(kaisyahoujinnbangoElement);
			}
		}

		//義務者セット
		Element gimusyaElement = docList.get(0).createElement("義務者");
		shinseijiko.item(0).appendChild(gimusyaElement);
		for(Gimusya gimusya : syoiten.getGimusyaList()) {
			Element meigininElement = docList.get(0).createElement("名義人情報");
			gimusyaElement.appendChild(meigininElement);

			Element addrElement = docList.get(0).createElement("住");
			addrElement.setTextContent(gimusya.getAddr());
			meigininElement.appendChild(addrElement);

			Element nameElement = docList.get(0).createElement("氏");
			nameElement.setTextContent(gimusya.getCustomer().getName());
			meigininElement.appendChild(nameElement);

			if(gimusya.getCustomer() instanceof Person) {

			} else {
				Element daitoriElement = docList.get(0).createElement("代");
				meigininElement.appendChild(daitoriElement);
				Element nyuryokuhouhouElement = docList.get(0).createElement("入力方法");
				nyuryokuhouhouElement.setTextContent("その他");
				daitoriElement.appendChild(nyuryokuhouhouElement);

				Element shikakuElement = docList.get(0).createElement("資格");
				shikakuElement.setTextContent(gimusya.getDaihyo().split("/")[0]);
				daitoriElement.appendChild(shikakuElement);

				Element daihyosyashimeiElement = docList.get(0).createElement("代表者氏名");
				daihyosyashimeiElement.setTextContent(gimusya.getDaihyo().split("/")[1]);
				daitoriElement.appendChild(daihyosyashimeiElement);

				Company company = (Company) gimusya.getCustomer();
				Element kaisyahoujinnbangoElement = docList.get(0).createElement("会社法人等番号");
				kaisyahoujinnbangoElement.setTextContent(company.getHoujinbango());
				meigininElement.appendChild(kaisyahoujinnbangoElement);
			}

			Element shikibetsuUmuElement = docList.get(0).createElement("識別情報提供区分");
			shikibetsuUmuElement.setTextContent(gimusya.getShikibetsuUmu());
			meigininElement.appendChild(shikibetsuUmuElement);

			Element shikibetsuRiyuElement = docList.get(0).createElement("識別情報未提供理由");
			shikibetsuRiyuElement.setTextContent(gimusya.getShikibetsuRiyu());
			meigininElement.appendChild(shikibetsuRiyuElement);
		}

		String tempsyorui = "";
		for(String syorui : syoiten.getTempsyorui().getSyoruis()) {
			tempsyorui += syorui + "\n";
		}
		ShinseiXmlHelper.elementTextset(syoitenElement, "添付情報", tempsyorui, 0);

		ShinseiXmlHelper.elementTextset(syoitenElement, "申請年月日", syoiten.getDate(),0);
		ShinseiXmlHelper.elementTextset(syoitenElement, "宛先登記所名", syoiten.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(syoitenElement, "提出先名称", syoiten.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(syoitenElement, "登記所コード", syoiten.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(syoitenElement, "提出先コード", syoiten.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(syoitenElement, "課税価格合計額", String.valueOf(syoiten.getKazeiGoukei()), 0);
		ShinseiXmlHelper.elementTextset(syoitenElement, "課税価格の内訳等", syoiten.getKazeiUchiwake(), 0);
		ShinseiXmlHelper.elementTextset(syoitenElement, "登録免許税合計額", String.valueOf(syoiten.getToumenGoukei()), 0);
		ShinseiXmlHelper.elementTextset(syoitenElement, "登録免許税内訳等", syoiten.getToumenUchiwake(), 0);
		ShinseiXmlHelper.elementTextset(syoitenElement, "登録免許税適用条項", syoiten.getJyobun(), 0);

		ShinseiXmlHelper.elementTextset(syoitenInfoElement, "Kemmei", syoiten.getKenmei(), 0);
		ShinseiXmlHelper.elementTextset(syoitenInfoElement, "ShinseisakiTokishoCode", syoiten.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(syoitenInfoElement, "ShinseisakiTokishoMeisho", syoiten.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(syoitenInfoElement, "SaishuKoshinNichiji", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), 0);
		ShinseiXmlHelper.elementTextset(syoitenExportElement, "ファイル名", syoiten.getKenmei() + ".zip", 0);

		//物件セット
		ShinseiXmlHelper.makeShinseiBukken(docList.get(0), syoiten);

		ShinseiXmlHelper.makeFolder(shinseiName, syoiten.getKenmei());

		ShinseiXmlHelper.makeShinseiFile(docList, shinseiName, syoiten.getKenmei());


        // ZIP化実施フォルダを取得
     	File destination = new File("./" + syoiten.getKenmei() + "s/" + syoiten.getKenmei() + ".zip");
     	File dirzip = new File("./" + syoiten.getKenmei());

     	// 圧縮実行
     	ZipCreate.compressDirectory(destination, dirzip);

     	// ZIP化実施フォルダを取得
     	File dir = new File("./" + syoiten.getKenmei() + "s");

     	// ZIP化実施フォルダを取得
     	File destination2 = new File("./" + syoiten.getKenmei() + ".zip");

     	// 圧縮実行
     	ZipCreate.compressDirectory(destination2, dir);


		return "所有権移転外部ファイル作成しました。";
	}
}

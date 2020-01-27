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
import com.example.demo.soft.entity.Saimusya;
import com.example.demo.soft.entity.Teitouken;
import com.example.demo.soft.repository.TeitoukenRepository;

@Service
public class TeitoukenService {

	@Autowired
	TeitoukenRepository repository;

	public Page<Teitouken> getAll(Pageable pageable){
		return repository.findAllByOrderByIdDesc(pageable);
	}

	public Teitouken find(Integer id) {
		return repository.findById(id).orElse(new Teitouken());
	}

	public Teitouken saveTeitouken(Teitouken teitouken) {
		return repository.saveAndFlush(teitouken);
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public String xmlFileGet(String shinseiName, Teitouken teitouken)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {

		// 1. DocumentBuilderFactoryのインスタンスを取得する
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// 2. DocumentBuilderのインスタンスを取得する
		DocumentBuilder builder = factory.newDocumentBuilder();

		// 3. DocumentBuilderにXMLを読み込ませ、Documentを作る
		List<Document> docList = ShinseiXmlHelper.getDocument(builder, shinseiName);

		// HM000000000.xml Element 取得
		Element teitouElement = ShinseiXmlHelper.xmlElement(docList);

		//info.xml element取得
		Element teitouInfoElement = ShinseiXmlHelper.infoElement(docList);

		//export.xml element取得
		Element teitouExportElement = ShinseiXmlHelper.exportElement(docList);


		//申請書組み立て
		ShinseiXmlHelper.elementTextset(teitouElement, "登記の目的", teitouken.getMokuteki(), 0);
		ShinseiXmlHelper.elementTextset(teitouElement, "原因", teitouken.getGenin(), 0);
		ShinseiXmlHelper.elementTextset(teitouElement, "債権額", teitouken.getSaikengaku(), 0);
		ShinseiXmlHelper.elementTextset(teitouElement, "利息", teitouken.getRisoku(), 0);
		ShinseiXmlHelper.elementTextset(teitouElement, "損害金", teitouken.getSongaikin(), 0);

		NodeList shinseijiko = docList.get(0).getElementsByTagName("申請事項");

		//債務者セット
		Element saimusyaElement = null;
		if(teitouken.getSaimusyaList().size() >= 2) {
			saimusyaElement = docList.get(0).createElement("連帯債務者");
		} else {
			saimusyaElement = docList.get(0).createElement("債務者");
		}

		shinseijiko.item(0).appendChild(saimusyaElement);
		for(Saimusya saimusya : teitouken.getSaimusyaList()) {
			Element meigininElement = docList.get(0).createElement("名義人情報");
			saimusyaElement.appendChild(meigininElement);

			Element addrElement = docList.get(0).createElement("住");
			addrElement.setTextContent(saimusya.getAddr());
			meigininElement.appendChild(addrElement);

			Element nameElement = docList.get(0).createElement("氏");
			nameElement.setTextContent(saimusya.getCustomer().getName());
			meigininElement.appendChild(nameElement);
		}

		//抵当権者セット
		Element kenrisyaElement = docList.get(0).createElement("抵当権者");
		shinseijiko.item(0).appendChild(kenrisyaElement);
		for(Kenrisya kenrisya : teitouken.getTeitoukensyaList()) {
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

			Element shikibetsuhakkoElement = docList.get(0).createElement("識別情報発行区分");
			shikibetsuhakkoElement.setTextContent("送付の方法による交付を希望する");
			meigininElement.appendChild(shikibetsuhakkoElement);
		}

		//設定者セット
		Element gimusyaElement = docList.get(0).createElement("設定者");
		shinseijiko.item(0).appendChild(gimusyaElement);
		for(Gimusya gimusya : teitouken.getGimusyaList()) {
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

				Element shikibetsuUmuElement = docList.get(0).createElement("識別情報提供区分");
				shikibetsuUmuElement.setTextContent(gimusya.getShikibetsuUmu());
				meigininElement.appendChild(shikibetsuUmuElement);

				Element shikibetsuRiyuElement = docList.get(0).createElement("識別情報未提供理由");
				shikibetsuRiyuElement.setTextContent(gimusya.getShikibetsuRiyu());
				meigininElement.appendChild(shikibetsuRiyuElement);
			}
		}

		String tempsyorui = "";
		for(String syorui : teitouken.getTempsyoruis().getSyoruis()) {
			tempsyorui += syorui + "\n";
		}
		ShinseiXmlHelper.elementTextset(teitouElement, "添付情報", tempsyorui, 0);

		ShinseiXmlHelper.elementTextset(teitouElement, "申請年月日", teitouken.getDate(),0);
		ShinseiXmlHelper.elementTextset(teitouElement, "宛先登記所名", teitouken.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(teitouElement, "提出先名称", teitouken.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(teitouElement, "登記所コード", teitouken.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(teitouElement, "提出先コード", teitouken.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(teitouElement, "課税価格合計額", String.valueOf(teitouken.getKazeiGoukei()), 0);
		ShinseiXmlHelper.elementTextset(teitouElement, "登録免許税合計額", String.valueOf(teitouken.getToumenGoukei()), 0);
		ShinseiXmlHelper.elementTextset(teitouElement, "登録免許税適用条項", teitouken.getJyobun(), 0);

		ShinseiXmlHelper.elementTextset(teitouInfoElement, "Kemmei", teitouken.getKenmei(), 0);
		ShinseiXmlHelper.elementTextset(teitouInfoElement, "ShinseisakiTokishoCode", teitouken.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(teitouInfoElement, "ShinseisakiTokishoMeisho", teitouken.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(teitouInfoElement, "SaishuKoshinNichiji", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), 0);
		ShinseiXmlHelper.elementTextset(teitouExportElement, "ファイル名", teitouken.getKenmei() + ".zip", 0);

		//物件セット
		ShinseiXmlHelper.makeShinseiBukken(docList.get(0), teitouken);

		ShinseiXmlHelper.makeFolder(shinseiName, teitouken.getKenmei());

		ShinseiXmlHelper.makeShinseiFile(docList, shinseiName, teitouken.getKenmei());


        // ZIP化実施フォルダを取得
     	File destination = new File("./" + teitouken.getKenmei() + "s/" + teitouken.getKenmei() + ".zip");
     	File dirzip = new File("./" + teitouken.getKenmei());

     	// 圧縮実行
     	ZipCreate.compressDirectory(destination, dirzip);

     	// ZIP化実施フォルダを取得
     	File dir = new File("./" + teitouken.getKenmei() + "s");

     	// ZIP化実施フォルダを取得
     	File destination2 = new File("./" + teitouken.getKenmei() + ".zip");

     	// 圧縮実行
     	ZipCreate.compressDirectory(destination2, dir);


		return "抵当権設定外部ファイル作成しました。";
	}
}

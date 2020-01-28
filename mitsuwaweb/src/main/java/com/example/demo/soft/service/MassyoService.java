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
import com.example.demo.soft.entity.Massyo;
import com.example.demo.soft.repository.MassyoRepository;

@Service
public class MassyoService {

	@Autowired
	MassyoRepository repository;

	public Page<Massyo> getAll(Pageable pageable){
		return repository.findAllByOrderByIdDesc(pageable);
	}

	public Massyo find(Integer id) {
		return repository.findById(id).orElse(new Massyo());
	}

	public Massyo saveMassyo(Massyo massyo) {
		return repository.saveAndFlush(massyo);
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public String xmlFileGet(String shinseiName, Massyo massyo)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {

		// 1. DocumentBuilderFactoryのインスタンスを取得する
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// 2. DocumentBuilderのインスタンスを取得する
		DocumentBuilder builder = factory.newDocumentBuilder();

		// 3. DocumentBuilderにXMLを読み込ませ、Documentを作る
		List<Document> docList = ShinseiXmlHelper.getDocument(builder, shinseiName);

		// HM000000000.xml Element 取得
		Element massyoElement = ShinseiXmlHelper.xmlElement(docList);

		//info.xml element取得
		Element massyoInfoElement = ShinseiXmlHelper.infoElement(docList);

		//export.xml element取得
		Element massyoExportElement = ShinseiXmlHelper.exportElement(docList);


		//申請書組み立て
		ShinseiXmlHelper.elementTextset(massyoElement, "登記の目的", massyo.getMokuteki(), 0);
		ShinseiXmlHelper.elementTextset(massyoElement, "元号", massyo.getMassyoGengo(), 0);
		ShinseiXmlHelper.elementTextset(massyoElement, "年", massyo.getMassyoYear(), 0);
		ShinseiXmlHelper.elementTextset(massyoElement, "月", massyo.getMassyoMonth(), 0);
		ShinseiXmlHelper.elementTextset(massyoElement, "日", massyo.getMassyoDay(), 0);
		ShinseiXmlHelper.elementTextset(massyoElement, "受付番号", massyo.getMassyoUkeban(), 0);
		ShinseiXmlHelper.elementTextset(massyoElement, "原因", massyo.getGenin(), 0);

		NodeList shinseijiko = docList.get(0).getElementsByTagName("申請事項");

		//権利者セット
		Element kenrisyaElement = docList.get(0).createElement("権利者");
		shinseijiko.item(0).appendChild(kenrisyaElement);
		for(Kenrisya kenrisya : massyo.getKenrisyaList()) {
			Element meigininElement = docList.get(0).createElement("名義人情報");
			kenrisyaElement.appendChild(meigininElement);

			Element addrElement = docList.get(0).createElement("住");
			addrElement.setTextContent(kenrisya.getAddr());
			meigininElement.appendChild(addrElement);

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
		for(Gimusya gimusya : massyo.getGimusyaList()) {
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
		for(String syorui : massyo.getTempsyorui().getSyoruis()) {
			tempsyorui += syorui + "\n";
		}
		ShinseiXmlHelper.elementTextset(massyoElement, "添付情報", tempsyorui, 0);

		ShinseiXmlHelper.elementTextset(massyoElement, "申請年月日", massyo.getDate(),0);
		ShinseiXmlHelper.elementTextset(massyoElement, "宛先登記所名", massyo.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(massyoElement, "提出先名称", massyo.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(massyoElement, "登記所コード", massyo.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(massyoElement, "提出先コード", massyo.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(massyoElement, "登録免許税合計額", String.valueOf(massyo.getToumenGoukei()), 0);
		ShinseiXmlHelper.elementTextset(massyoElement, "登録免許税適用条項", massyo.getJyobun(), 0);

		ShinseiXmlHelper.elementTextset(massyoInfoElement, "Kemmei", massyo.getKenmei(), 0);
		ShinseiXmlHelper.elementTextset(massyoInfoElement, "ShinseisakiTokishoCode", massyo.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(massyoInfoElement, "ShinseisakiTokishoMeisho", massyo.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(massyoInfoElement, "SaishuKoshinNichiji", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), 0);
		ShinseiXmlHelper.elementTextset(massyoExportElement, "ファイル名", massyo.getKenmei() + ".zip", 0);

		//物件セット
		ShinseiXmlHelper.makeShinseiBukken(docList.get(0), massyo);

		ShinseiXmlHelper.makeFolder(shinseiName, massyo.getKenmei());

		ShinseiXmlHelper.makeShinseiFile(docList, shinseiName, massyo.getKenmei());


        // ZIP化実施フォルダを取得
     	File destination = new File("./" + massyo.getKenmei() + "s/" + massyo.getKenmei() + ".zip");
     	File dirzip = new File("./" + massyo.getKenmei());

     	// 圧縮実行
     	ZipCreate.compressDirectory(destination, dirzip);

     	// ZIP化実施フォルダを取得
     	File dir = new File("./" + massyo.getKenmei() + "s");

     	// ZIP化実施フォルダを取得
     	File destination2 = new File("./" + massyo.getKenmei() + ".zip");

     	// 圧縮実行
     	ZipCreate.compressDirectory(destination2, dir);


		return "抹消外部ファイル作成しました。";
	}
}

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
import com.example.demo.soft.entity.Meihen;
import com.example.demo.soft.repository.MeihenRepository;

@Service
public class MeihenService {

	@Autowired
	MeihenRepository repository;

	public Page<Meihen> getAll(Pageable pageable){
		return repository.findAllByOrderByIdDesc(pageable);
	}

	public Meihen find(Integer id) {
		return repository.findById(id).orElse(new Meihen());
	}

	public Meihen saveMeihen(Meihen meihen) {
		return repository.saveAndFlush(meihen);
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public String xmlFileGet(String shinseiName, Meihen meihen)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {

		// 1. DocumentBuilderFactoryのインスタンスを取得する
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// 2. DocumentBuilderのインスタンスを取得する
		DocumentBuilder builder = factory.newDocumentBuilder();

		// 3. DocumentBuilderにXMLを読み込ませ、Documentを作る
		List<Document> docList = ShinseiXmlHelper.getDocument(builder, shinseiName);

		// HM000000000.xml Element 取得
		Element meihenElement = ShinseiXmlHelper.xmlElement(docList);

		//info.xml element取得
		Element meihenInfoElement = ShinseiXmlHelper.infoElement(docList);

		//export.xml element取得
		Element meihenExportElement = ShinseiXmlHelper.exportElement(docList);


		//申請書組み立て
		ShinseiXmlHelper.elementTextset(meihenElement, "登記の目的", meihen.getMokuteki(), 0);
		ShinseiXmlHelper.elementTextset(meihenElement, "原因", meihen.getGenin(), 0);

		NodeList shinseijiko = docList.get(0).getElementsByTagName("申請事項");

		Element henkouElement = docList.get(0).createElement("変更後の事項");
		shinseijiko.item(0).appendChild(henkouElement);

		Element jikomeiElement = docList.get(0).createElement("事項名");
		jikomeiElement.setTextContent(meihen.getHenkojiko());
		henkouElement.appendChild(jikomeiElement);

		Element jikonaiyoElement = docList.get(0).createElement("事項内容");
		henkouElement.appendChild(jikonaiyoElement);

		Element henkoMeigininElement = docList.get(0).createElement("名義人情報");
		jikonaiyoElement.appendChild(henkoMeigininElement);
		Element jyuElement = docList.get(0).createElement("住");
		jyuElement.setTextContent(meihen.getShinseininList().get(0).getAddr());
		henkoMeigininElement.appendChild(jyuElement);

		//申請人セット
		Element shinseininElement = docList.get(0).createElement("申請人");
		shinseijiko.item(0).appendChild(shinseininElement);
		for(Gimusya gimusya : meihen.getShinseininList()) {
			Element meigininElement = docList.get(0).createElement("名義人情報");
			shinseininElement.appendChild(meigininElement);

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
		}

		String tempsyorui = "";
		for(String syorui : meihen.getTempsyorui().getSyoruis()) {
			tempsyorui += syorui + "\n";
		}
		ShinseiXmlHelper.elementTextset(meihenElement, "添付情報", tempsyorui, 0);

		ShinseiXmlHelper.elementTextset(meihenElement, "申請年月日", meihen.getDate(),0);
		ShinseiXmlHelper.elementTextset(meihenElement, "宛先登記所名", meihen.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(meihenElement, "提出先名称", meihen.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(meihenElement, "登記所コード", meihen.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(meihenElement, "提出先コード", meihen.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(meihenElement, "登録免許税合計額", String.valueOf(meihen.getToumenGoukei()), 0);
		ShinseiXmlHelper.elementTextset(meihenElement, "登録免許税適用条項", meihen.getJyobun(), 0);

		ShinseiXmlHelper.elementTextset(meihenInfoElement, "Kemmei", meihen.getKenmei(), 0);
		ShinseiXmlHelper.elementTextset(meihenInfoElement, "ShinseisakiTokishoCode", meihen.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(meihenInfoElement, "ShinseisakiTokishoMeisho", meihen.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(meihenInfoElement, "SaishuKoshinNichiji", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), 0);
		ShinseiXmlHelper.elementTextset(meihenExportElement, "ファイル名", meihen.getKenmei() + ".zip", 0);

		//物件セット
		ShinseiXmlHelper.makeShinseiBukken(docList.get(0), meihen);

		ShinseiXmlHelper.makeFolder(shinseiName, meihen.getKenmei());

		ShinseiXmlHelper.makeShinseiFile(docList, shinseiName, meihen.getKenmei());


        // ZIP化実施フォルダを取得
     	File destination = new File("./" + meihen.getKenmei() + "s/" + meihen.getKenmei() + ".zip");
     	File dirzip = new File("./" + meihen.getKenmei());

     	// 圧縮実行
     	ZipCreate.compressDirectory(destination, dirzip);

     	// ZIP化実施フォルダを取得
     	File dir = new File("./" + meihen.getKenmei() + "s");

     	// ZIP化実施フォルダを取得
     	File destination2 = new File("./" + meihen.getKenmei() + ".zip");

     	// 圧縮実行
     	ZipCreate.compressDirectory(destination2, dir);


		return "名変外部ファイル作成しました。";
	}
}

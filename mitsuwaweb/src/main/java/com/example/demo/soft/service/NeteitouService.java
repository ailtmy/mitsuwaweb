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
import com.example.demo.soft.entity.Neteitou;
import com.example.demo.soft.entity.Saimusya;
import com.example.demo.soft.repository.NeteitouRepository;

@Service
public class NeteitouService {

	@Autowired
	NeteitouRepository repository;

	public Page<Neteitou> getAll(Pageable pageable){
		return repository.findAllByOrderByIdDesc(pageable);
	}

	public Neteitou find(Integer id) {
		return repository.findById(id).orElse(new Neteitou());
	}

	public Neteitou saveNeteitou(Neteitou neteitou) {
		return repository.saveAndFlush(neteitou);
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public String xmlFileGet(String shinseiName, Neteitou neteitou)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {

		// 1. DocumentBuilderFactoryのインスタンスを取得する
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// 2. DocumentBuilderのインスタンスを取得する
		DocumentBuilder builder = factory.newDocumentBuilder();

		// 3. DocumentBuilderにXMLを読み込ませ、Documentを作る
		List<Document> docList = ShinseiXmlHelper.getDocument(builder, shinseiName);

		// HM000000000.xml Element 取得
		Element neteitouElement = ShinseiXmlHelper.xmlElement(docList);

		//info.xml element取得
		Element neteitouInfoElement = ShinseiXmlHelper.infoElement(docList);

		//export.xml element取得
		Element neteitouExportElement = ShinseiXmlHelper.exportElement(docList);


		//申請書組み立て
		ShinseiXmlHelper.elementTextset(neteitouElement, "登記の目的", neteitou.getMokuteki(), 0);
		ShinseiXmlHelper.elementTextset(neteitouElement, "原因", neteitou.getGenin(), 0);
		ShinseiXmlHelper.elementTextset(neteitouElement, "極度額", neteitou.getSaikengaku(), 0);
		ShinseiXmlHelper.elementTextset(neteitouElement, "債権の範囲", neteitou.getSaikenhanni(), 0);

		NodeList shinseijiko = docList.get(0).getElementsByTagName("申請事項");
		if(neteitou.getKakuteikijitsu() == null || neteitou.getKakuteikijitsu().isEmpty()) {

		} else {
			Element kakuteikijitsuElement = docList.get(0).createElement("確定期日");
			kakuteikijitsuElement.setTextContent(neteitou.getKakuteikijitsu());
			shinseijiko.item(0).appendChild(kakuteikijitsuElement);
		}

		//債務者セット
		Element saimusyaElement = docList.get(0).createElement("債務者");

		shinseijiko.item(0).appendChild(saimusyaElement);
		for(Saimusya saimusya : neteitou.getSaimusyaList()) {
			Element meigininElement = docList.get(0).createElement("名義人情報");
			saimusyaElement.appendChild(meigininElement);

			Element addrElement = docList.get(0).createElement("住");
			addrElement.setTextContent(saimusya.getAddr());
			meigininElement.appendChild(addrElement);

			Element nameElement = docList.get(0).createElement("氏");
			nameElement.setTextContent(saimusya.getCustomer().getName());
			meigininElement.appendChild(nameElement);
		}

		//根抵当権者セット
		Element kenrisyaElement = docList.get(0).createElement("根抵当権者");
		shinseijiko.item(0).appendChild(kenrisyaElement);
		for(Kenrisya kenrisya : neteitou.getNeteitoukensyaList()) {
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
				if(kenrisya.getShiten().isEmpty() || kenrisya.getShiten() == null) {

				} else {
					Element shitenElement = docList.get(0).createElement("取");
					shitenElement.setTextContent(kenrisya.getShiten());
					meigininElement.appendChild(shitenElement);
				}

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
		for(Gimusya gimusya : neteitou.getGimusyaList()) {
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
		for(String syorui : neteitou.getTempsyorui().getSyoruis()) {
			tempsyorui += syorui + "\n";
		}
		ShinseiXmlHelper.elementTextset(neteitouElement, "添付情報", tempsyorui, 0);

		ShinseiXmlHelper.elementTextset(neteitouElement, "申請年月日", neteitou.getDate(),0);
		ShinseiXmlHelper.elementTextset(neteitouElement, "宛先登記所名", neteitou.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(neteitouElement, "提出先名称", neteitou.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(neteitouElement, "登記所コード", neteitou.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(neteitouElement, "提出先コード", neteitou.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(neteitouElement, "課税価格合計額", String.valueOf(neteitou.getKazeiGoukei()), 0);
		ShinseiXmlHelper.elementTextset(neteitouElement, "登録免許税合計額", String.valueOf(neteitou.getToumenGoukei()), 0);
		ShinseiXmlHelper.elementTextset(neteitouElement, "登録免許税適用条項", neteitou.getJyobun(), 0);

		ShinseiXmlHelper.elementTextset(neteitouInfoElement, "Kemmei", neteitou.getKenmei(), 0);
		ShinseiXmlHelper.elementTextset(neteitouInfoElement, "ShinseisakiTokishoCode", neteitou.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(neteitouInfoElement, "ShinseisakiTokishoMeisho", neteitou.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(neteitouInfoElement, "SaishuKoshinNichiji", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), 0);
		ShinseiXmlHelper.elementTextset(neteitouExportElement, "ファイル名", neteitou.getKenmei() + ".zip", 0);

		//物件セット
		ShinseiXmlHelper.makeShinseiBukken(docList.get(0), neteitou);

		ShinseiXmlHelper.makeFolder(shinseiName, neteitou.getKenmei());

		ShinseiXmlHelper.makeShinseiFile(docList, shinseiName, neteitou.getKenmei());


        // ZIP化実施フォルダを取得
     	File destination = new File("./" + neteitou.getKenmei() + "s/" + neteitou.getKenmei() + ".zip");
     	File dirzip = new File("./" + neteitou.getKenmei());

     	// 圧縮実行
     	ZipCreate.compressDirectory(destination, dirzip);

     	// ZIP化実施フォルダを取得
     	File dir = new File("./" + neteitou.getKenmei() + "s");

     	// ZIP化実施フォルダを取得
     	File destination2 = new File("./" + neteitou.getKenmei() + ".zip");

     	// 圧縮実行
     	ZipCreate.compressDirectory(destination2, dir);


		return "抵当権設定外部ファイル作成しました。";
	}
}

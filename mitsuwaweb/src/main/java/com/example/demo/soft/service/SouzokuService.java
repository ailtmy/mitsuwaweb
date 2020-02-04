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
import com.example.demo.soft.entity.Souzoku;
import com.example.demo.soft.repository.SouzokuRepository;

@Service
public class SouzokuService {

	@Autowired
	SouzokuRepository repository;

	public Page<Souzoku> getAll(Pageable pageable){
		return repository.findAllByOrderByIdDesc(pageable);
	}

	public Souzoku find(Integer id) {
		return repository.findById(id).orElse(new Souzoku());
	}

	public Souzoku saveSouzoku(Souzoku souzoku) {
		return repository.saveAndFlush(souzoku);
	}

	public void delete(Integer id) {
		repository.deleteById(id);
	}

	public String xmlFileGet(String shinseiName, Souzoku souzoku) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		// 1. DocumentBuilderFactoryのインスタンスを取得する
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// 2. DocumentBuilderのインスタンスを取得する
		DocumentBuilder builder = factory.newDocumentBuilder();

		// 3. DocumentBuilderにXMLを読み込ませ、Documentを作る
		List<Document> docList = ShinseiXmlHelper.getDocument(builder, shinseiName);

		// HM000000000.xml Element 取得
		Element souzokuElement = ShinseiXmlHelper.xmlElement(docList);

		//info.xml element取得
		Element souzokuInfoElement = ShinseiXmlHelper.infoElement(docList);

		//export.xml element取得
		Element souzokuExportElement = ShinseiXmlHelper.exportElement(docList);

		ShinseiXmlHelper.elementTextset(souzokuElement, "登記の目的", souzoku.getMokuteki(),0);
		ShinseiXmlHelper.elementTextset(souzokuElement, "原因", souzoku.getGenin(),0);

		//相続人セット
		Element souzokuninElement = docList.get(0).createElement("相続人");
		NodeList shinseijiko = docList.get(0).getElementsByTagName("申請事項");
		shinseijiko.item(0).appendChild(souzokuninElement);

		for(int i = 0; i < souzoku.getSouzokuninList().size(); i++) {
			Element meigininElement = docList.get(0).createElement("名義人情報");
			souzokuninElement.appendChild(meigininElement);

			if(i == 0) {
				Element hisouzokuninElement = docList.get(0).createElement("被相続人");
				hisouzokuninElement.setTextContent(souzoku.getHisouzokunin());
				meigininElement.appendChild(hisouzokuninElement);
			}

			Element addrElement = docList.get(0).createElement("住");
			addrElement.setTextContent(souzoku.getSouzokuninList().get(i).getAddr());
			meigininElement.appendChild(addrElement);

			if(souzoku.getSouzokuninList().get(i).getMochibun().isEmpty() ||
					souzoku.getSouzokuninList().get(i).getMochibun() == null) {

			} else {
				Element mochiElement = docList.get(0).createElement("持");
				mochiElement.setTextContent(souzoku.getSouzokuninList().get(i).getMochibun());
				meigininElement.appendChild(mochiElement);
			}
			Element nameElement = docList.get(0).createElement("氏");
			nameElement.setTextContent(souzoku.getSouzokuninList().get(i).getCustomer().getName());
			meigininElement.appendChild(nameElement);

			if(souzoku.getSouzokuninList().get(i).getCustomer() instanceof Person) {

			} else {
				Element daitoriElement = docList.get(0).createElement("代");
				meigininElement.appendChild(daitoriElement);
				Element nyuryokuhouhouElement = docList.get(0).createElement("入力方法");
				nyuryokuhouhouElement.setTextContent("その他");
				daitoriElement.appendChild(nyuryokuhouhouElement);

				Element shikakuElement = docList.get(0).createElement("資格");
				shikakuElement.setTextContent(souzoku.getSouzokuninList().get(i).getDaihyo().split("/")[0]);
				daitoriElement.appendChild(shikakuElement);

				Element daihyosyashimeiElement = docList.get(0).createElement("代表者氏名");
				daihyosyashimeiElement.setTextContent(souzoku.getSouzokuninList().get(i).getDaihyo().split("/")[1]);
				daitoriElement.appendChild(daihyosyashimeiElement);

				Company company = (Company) souzoku.getSouzokuninList().get(i).getCustomer();
				Element kaisyahoujinnbangoElement = docList.get(0).createElement("会社法人等番号");
				kaisyahoujinnbangoElement.setTextContent(company.getHoujinbango());
				meigininElement.appendChild(kaisyahoujinnbangoElement);
			}

			Element shikibetsuhakkoElement = docList.get(0).createElement("識別情報発行区分");
			shikibetsuhakkoElement.setTextContent("送付の方法による交付を希望する");
			meigininElement.appendChild(shikibetsuhakkoElement);

		}

		String tempsyorui = "";
		for(String syorui : souzoku.getTempsyorui().getSyoruis()) {
			tempsyorui += syorui + "\n";
		}
		ShinseiXmlHelper.elementTextset(souzokuElement, "添付情報", tempsyorui, 0);

		ShinseiXmlHelper.elementTextset(souzokuElement, "申請年月日", souzoku.getDate(),0);
		ShinseiXmlHelper.elementTextset(souzokuElement, "宛先登記所名", souzoku.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(souzokuElement, "提出先名称", souzoku.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(souzokuElement, "登記所コード", souzoku.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(souzokuElement, "提出先コード", souzoku.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(souzokuElement, "課税価格合計額", String.valueOf(souzoku.getKazeiGoukei()), 0);
		ShinseiXmlHelper.elementTextset(souzokuElement, "課税価格の内訳等", souzoku.getKazeiUchiwake(), 0);
		ShinseiXmlHelper.elementTextset(souzokuElement, "登録免許税合計額", String.valueOf(souzoku.getToumenGoukei()), 0);
		ShinseiXmlHelper.elementTextset(souzokuElement, "登録免許税内訳等", souzoku.getToumenUchiwake(), 0);
		ShinseiXmlHelper.elementTextset(souzokuElement, "登録免許税適用条項", souzoku.getJyobun(), 0);

		ShinseiXmlHelper.elementTextset(souzokuInfoElement, "Kemmei", souzoku.getKenmei(), 0);
		ShinseiXmlHelper.elementTextset(souzokuInfoElement, "ShinseisakiTokishoCode", souzoku.getTokisyo().getTokisyoCode().toString(), 0);
		ShinseiXmlHelper.elementTextset(souzokuInfoElement, "ShinseisakiTokishoMeisho", souzoku.getTokisyo().getTokisyoName(), 0);
		ShinseiXmlHelper.elementTextset(souzokuInfoElement, "SaishuKoshinNichiji", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), 0);
		ShinseiXmlHelper.elementTextset(souzokuExportElement, "ファイル名", souzoku.getKenmei() + ".zip", 0);

		//物件セット
		ShinseiXmlHelper.makeShinseiBukken(docList.get(0), souzoku);

		ShinseiXmlHelper.makeFolder(shinseiName, souzoku.getKenmei());

		ShinseiXmlHelper.makeShinseiFile(docList, shinseiName, souzoku.getKenmei());


        // ZIP化実施フォルダを取得
     	File destination = new File("./" + souzoku.getKenmei() + "s/" + souzoku.getKenmei() + ".zip");
     	File dirzip = new File("./" + souzoku.getKenmei());

     	// 圧縮実行
     	ZipCreate.compressDirectory(destination, dirzip);

     	// ZIP化実施フォルダを取得
     	File dir = new File("./" + souzoku.getKenmei() + "s");

     	// ZIP化実施フォルダを取得
     	File destination2 = new File("./" + souzoku.getKenmei() + ".zip");

     	// 圧縮実行
     	ZipCreate.compressDirectory(destination2, dir);

		return "相続外部ファイルを作成しました。";
	}
}

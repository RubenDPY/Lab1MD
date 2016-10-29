package codigo;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.SparseToNonSparse;

public class Preprocess {
	private Instances data;
	
	public Preprocess(Instances pData){
		data = pData;
	}
	public Instances getData(){
		return data;
	}
	public void TF_IDF() throws Exception{
		StringToWordVector filter= new StringToWordVector();		
		filter.setOutputWordCounts(true);
		filter.setLowerCaseTokens(true);
		filter.setTFTransform(true);
		filter.setIDFTransform(true);
		filter.setWordsToKeep(100);
		filter.setInputFormat(data);
		Instances newData = Filter.useFilter(data, filter);
		data = newData;
		this.SparseToNonSparse();
	}
	public void SparseToNonSparse() throws Exception{
		SparseToNonSparse filter= new SparseToNonSparse();
		filter.setInputFormat(data);
		Instances newData = Filter.useFilter(data, filter);
		data = newData;
	}
}

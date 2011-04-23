package edu.washington.cs.knowitall.extractor.conf;

import java.io.IOException;

import weka.classifiers.Classifier;
import edu.washington.cs.knowitall.nlp.extraction.ChunkedBinaryExtraction;
import edu.washington.cs.knowitall.util.DefaultObjects;

/***
 * An extraction confidence function that is backed by a logistic regression
 * classifier. This function will assign an extraction a real valued number between
 * 0 and 1 according to the logistic regression model.
 * 
 * It represents an extraction using the boolean features defined by the 
 * <code>ReVerbFeatures</code> class. See that documentation for details.
 * 
 * It uses the model returned by <code>DefaultObjects.getDefaultConfClassifier</code>,
 * which searches the classpath for a file called "conf.weka". 
 * 
 * @author afader
 *
 */
public class ReVerbConfFunction {
	
	private Classifier classifier;
	private ReVerbFeatures reverbFeatures;
	private BooleanFeatureSet<ChunkedBinaryExtraction> featureSet;
	private WekaClassifierConfFunction<ChunkedBinaryExtraction> func;
	
	/**
	 * Constructs a new instance of the confidence function.
	 * @throws ConfidenceFunctionException if unable to initialize
	 */
	public ReVerbConfFunction() throws ConfidenceFunctionException {
		try {
			classifier = DefaultObjects.getDefaultConfClassifier();
			reverbFeatures = new ReVerbFeatures();
			featureSet = reverbFeatures.getFeatureSet();
			func = new WekaClassifierConfFunction<ChunkedBinaryExtraction>(
				featureSet, classifier);
		} catch (IOException e) {
			throw new ConfidenceFunctionException(
				"Unable to load classifier", e);
		}
	}
	
	/**
	 * @param extr
	 * @return the probability that the given extraction belongs to the positive class
	 * @throws ConfidenceFunctionException if unable to compute the confidence score
	 */
	public double getConf(ChunkedBinaryExtraction extr) throws ConfidenceFunctionException {
        try {
            return func.getConf(extr);
        }
        catch (Exception e) {
            throw new ConfidenceFunctionException(e);
        }
	}
}
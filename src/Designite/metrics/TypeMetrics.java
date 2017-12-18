package Designite.metrics;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.TypeDeclaration;

import Designite.SourceModel.AccessStates;
import Designite.SourceModel.SM_Field;
import Designite.SourceModel.SM_Method;
import Designite.SourceModel.SM_Type;

public class TypeMetrics implements MetricExtractor {
	
	private int numOfFields;
	private int numOfPublicFields;
	private int numOfMethods;
	private int numOfPublicMethods;
	private int depthOfInheritance;
	private int numOfLines;
	private int numOfChildren;
	private int weightedMethodsPerClass;
	
	private List<SM_Field> fieldList;
	private List<SM_Method> methodList;
	private List<SM_Type> superTypes;
	private List<SM_Type> subTypes;
	private TypeDeclaration typeDeclaration;
	
	public TypeMetrics(List<SM_Field> fieldList
			, List<SM_Method> methodList
			, List<SM_Type> superTypes
			, List<SM_Type> subTypes
			, TypeDeclaration typeDeclaration) {
		this.fieldList = fieldList;
		this.methodList = methodList;
		this.superTypes = superTypes;
		this.subTypes = subTypes;
		this.typeDeclaration = typeDeclaration;
		
		subTypes = new ArrayList<>();
	}
	
	@Override
	public void extractMetrics() {
		extractNumOfFieldMetrics();
		extractNumOfMethodsMetrics();
		extractDepthOfInheritance();
		extractNumberOfLines();
		extractNumberOfChildren();
		extractWeightedMethodsPerClass();
	}
	
	private void extractNumOfFieldMetrics() {
		for (SM_Field field : fieldList) {
			numOfFields++;
			if (field.getAccessModifier() == AccessStates.PUBLIC) {
				numOfPublicFields++;
			}	
		}
	}
	
	private void extractNumOfMethodsMetrics() {
		for (SM_Method method : methodList) {
			numOfMethods++;
			if (method.getAccessModifier() == AccessStates.PUBLIC) {
				numOfPublicMethods++;
			}
		}
	}
	
	private void extractDepthOfInheritance() {
		depthOfInheritance += findInheritanceDepth(superTypes);
	}
	
	private void extractNumberOfLines() {
		String body = typeDeclaration.toString();
		numOfLines = body.length() - body.replace("\n", "").length();
	}
	
	private void extractNumberOfChildren() {
		numOfChildren = subTypes.size();
	}
	
	private int findInheritanceDepth(List<SM_Type> superTypes) {
		if (superTypes.size() == 0) {
			return 0;
		}
		return findInheritanceDepth(superTypes.get(0).getSuperTypes()) + 1;
	}
	
	private void extractWeightedMethodsPerClass() {
		for (SM_Method method : methodList) {
			weightedMethodsPerClass += method.getMethodMetrics().getCyclomaticComplexity();
		} 
	}

	public int getNumOfFields() {
		return numOfFields;
	}

	public int getNumOfPublicFields() {
		return numOfPublicFields;
	}
	
	public int getNumOfMethods() {
		return numOfMethods;
	}
	
	public int getNumOfPublicMethods() {
		return numOfPublicMethods;
	}
	
	public int getInheritanceDepth() {
		return depthOfInheritance;
	}

	public int getNumOfLines() {
		return numOfLines;
	}

	public int getNumOfChildren() {
		return numOfChildren;
	}

	public int getWeightedMethodsPerClass() {
		return weightedMethodsPerClass;
	}
	
}

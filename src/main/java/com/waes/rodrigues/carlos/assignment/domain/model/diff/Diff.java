package com.waes.rodrigues.carlos.assignment.domain.model.diff;

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waes.rodrigues.carlos.assignment.domain.model.EntityModel;

/**
 * 
 * Entity to store left and right values as String JSON.
 * And so, providing insights from those values.
 * 
 * <p> All values are needed to provide insights.
 * 
 * 
 * @author carlos
 *
 */

@Entity
@Table(name = "WAESDIFF")
@Access(AccessType.FIELD)
public class Diff extends EntityModel implements IDiff{
	
	private static final long serialVersionUID = -4087473245396611252L;

	@Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
	
	@Column(name = "left")
	private String left;
	@Column(name = "right")
	private String right;
	
	public Diff(){}
	
	public Diff(IDiff diff){
		this.id = diff.getId();
		this.left = diff.getLeft();
		this.right = diff.getRight();
	}
	
	
	/**
	 * Validating constraint rules.
	 * 
	 * <p> If pass over constraints, we can get the insights.
	 * 
	 * @throws DiffException
	 */
	public void validateConditionsToInsight() throws DiffException{
		try {
			if (StringUtils.isBlank(this.left)) {
				throw new DiffException(messageBundle.getString("error.msg.blank.text.side", "left"));
			} else if (StringUtils.isBlank(this.right)) {
				throw new DiffException(messageBundle.getString("error.msg.blank.text.side", "right"));
			}
			Validate.isTrue(!isSameEncoded(), messageBundle.getString("warn.msg.contents.equal"));
			Validate.isTrue(isSameSize(), messageBundle.getString("warn.msg.diff.size"));
		} catch(IllegalArgumentException e){
			throw new DiffException(e.getMessage());
		}catch (DiffException e) {
			throw e;
		}
	}
	
	/**
	 * 
	 * the JSON format returned will be:
	 * <p> {
	 * 			"author":"Carlos",
	 * 			"age":"37"	
	 * 		}
	 * 
	 * @return a String as JSON
	 * @throws DiffException
	 */
	public String getInsight() throws DiffException{
		validateConditionsToInsight();
		int lengthDiff=0;
		StringBuffer offSets = new StringBuffer();
		for(int i=0; i<left.length(); i++){
			if(left.charAt(i) != right.charAt(i)){
				lengthDiff++;
				offSets.append(i);
				if(i<left.length()-2){
					offSets.append(",");
				}
			}
		}
		return insightToJSON(lengthDiff, offSets);
	}

	private String insightToJSON(int lengthDiff, StringBuffer offSets) throws DiffException {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("offsets", offSets.toString());
			jsonObject.put("length", lengthDiff);
			return jsonObject.toString();
		} catch (JSONException e) {
			throw new DiffException(messageBundle.getString("error.msg.convert.json"));
		}
	}
	
	
	public boolean isSameSize() throws DiffException {
		return this.left.length() == this.right.length();
	}
	
	public boolean isSameValues() {
		return new EqualsBuilder()
				.append(this.left, this.right)
				.isEquals();
	}

	/**
	 * 
	 * Compare values as Base64, it would does more sense on a JSON bigger.
	 * 
	 * @return
	 * @throws DiffException
	 */
	public boolean isSameEncoded() throws DiffException {
		Encoder enc = Base64.getEncoder();
		try {
			return new EqualsBuilder()
					.append(enc.encode(this.left.getBytes()), enc.encode(this.right.getBytes()))
					.isEquals();
		} catch (Exception e) {
			throw new DiffException(messageBundle.getString("error.msg.compare.bytes"));
		}
	}
	
	public void updateLeft(final String left) throws DiffException{
		Validate.validState(StringUtils.isNoneBlank(left), messageBundle.getString("error.msg.blank.text"));
		ValueVO vo;
		try {
			vo = new ObjectMapper().readValue(left, ValueVO.class);
			vo.validate();
			this.left = left;
		} catch(IllegalStateException e){
			throw new DiffException(e.getMessage());
		}catch (IOException e) {
			throw new DiffException(messageBundle.getString("error.msg.convert.json"));
		} 
	}
	
	public void updateRight(final String right) throws DiffException{
		Validate.validState(StringUtils.isNoneBlank(right), messageBundle.getString("error.msg.blank.text"));
		ValueVO vo;
		try {
			vo = new ObjectMapper().readValue(right, ValueVO.class);
			vo.validate();
			this.right = right;
		} catch(IllegalStateException e){
			throw new DiffException(e.getMessage());
		}catch (IOException e) {
			throw new DiffException(messageBundle.getString("error.msg.convert.json"));
		} 
	}
	
	
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public String getLeft() {
		return this.left;
	}

	@Override
	public String getRight() {
		return this.right;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(this.id)
				.append(this.left)
				.append(this.right)
				.build();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Diff)) {
			return false;
		}
		Diff other = (Diff) obj;
		return new EqualsBuilder()
				.append(this.id, other.getId())
				.append(this.left, other.getLeft())
				.append(this.right, other.getRight())
				.isEquals();
	}

}
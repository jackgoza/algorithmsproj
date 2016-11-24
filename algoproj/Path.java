/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoproj;

/**
 *
 * @author jack
 */
public class Path {

    public Path(Integer s, Integer e, Integer f) {
	this.pStart = s;
	this.pEnd = e;
	this.pFlow = f;
    }

    public Integer pStart;
    public Integer pEnd;
    public Integer pFlow;

}

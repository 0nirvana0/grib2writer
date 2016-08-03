package grib.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lq.grib2.writer.Grib2Writer;

/**
	产品类型          单位          产品状态            种类                参数	统计处理方式
				    C		N		S
1	累计降水	kg m-2	0		1		8		1
2	平均温度	K		0		0		0		0
3	最低温度	K		0		0		0		3	
4	最高温度	K		0		0		0		2
5	相对湿度	%		0		1		1		0
6	最小相对湿度%		0		1		1		3	
7	最大相对湿度%		0		1		1		2
8	能见度	m		0		19		0		0
9	云		%		0		6		1		0
10	风-U		m s-1	0		2		2		0
11	风-V		m s-1	0		2		3		0
12	高温				0		0		201		0
13	雾				0		13		201		0
14	霾				0		13		202		0
15	强降水			0		1		201		0
16	强对流			0		1		202		201
*/
public class WriteGrib2Test {
	public static void main(String[] args) {
		List<float[]> datas = new ArrayList<float[]>();
		float[] d = new float[] { 1.234f, 2.345f, 3.567f, 4.8f, 5.9f, 6, 7, 8, 9 };
		float[] d2 = new float[] { 11.444f, 12, 13, 14, 15, 16, 17, 18, 19 };
		float[] d3 = new float[] { 211.555f, 212, 213, 214, 215, 216, 217, 218, 219 };
		datas.add(d);
		datas.add(d2);
		datas.add(d3);
		String path = "E:/grib2testGRIB2.GRIB2";
		Grib2Writer grib2 = null;
		try {
			grib2 = Grib2Writer.createNew(path);
			grib2.setReferenceDate(2016, 7, 29, 20, 0, 0);
			grib2.setGridDefinition(3, 3, 102, 25, 104, 27);
			grib2.create();
			for (int i = 0; i < datas.size(); i++) {
				grib2.write(0, 0, 0, i * 3, datas.get(i));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (grib2 != null) {
					grib2.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

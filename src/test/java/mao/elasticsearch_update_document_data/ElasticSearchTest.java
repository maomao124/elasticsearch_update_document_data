package mao.elasticsearch_update_document_data;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.get.GetResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * Project name(项目名称)：elasticsearch_update_document_data
 * Package(包名): mao.elasticsearch_update_document_data
 * Class(类名): ElasticSearchTest
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/5/25
 * Time(创建时间)： 21:20
 * Version(版本): 1.0
 * Description(描述)： SpringBootTest
 * <p>
 * 请求：
 * POST book/_update/5
 * {
 *      "doc":
 *      {
 *          "price" : 68.5
 *      }
 * }
 */

@SpringBootTest
public class ElasticSearchTest
{
    private static RestHighLevelClient client;

    @BeforeAll
    static void beforeAll()
    {
        client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }

    /**
     * 同步更新
     *
     * @throws IOException IOException
     */
    @Test
    void update() throws IOException
    {
        UpdateRequest updateRequest = new UpdateRequest("book", "5");
        //设置请求体
        updateRequest.doc("price", 68.5);

        //发起请求
        UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
        //获取数据
        GetResult getResult = updateResponse.getGetResult();
        System.out.println(getResult);
    }

    /**
     * 异步更新
     *
     * @throws IOException IOException
     */
    @Test
    void update_async() throws IOException
    {
        UpdateRequest updateRequest = new UpdateRequest("book", "5");
        //设置请求体
        updateRequest.doc("price", 68.5);

        //发起异步请求
        client.updateAsync(updateRequest, RequestOptions.DEFAULT, new ActionListener<UpdateResponse>()
        {
            @Override
            public void onResponse(UpdateResponse updateResponse)
            {
                //获取数据
                GetResult getResult = updateResponse.getGetResult();
                System.out.println(getResult);
            }

            @Override
            public void onFailure(Exception e)
            {
                e.printStackTrace();
            }
        });
        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}

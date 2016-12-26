package com.webex.cws.sample.aws;

import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.*;

/**
 * Created by yuzhou2 on 12/26/16.
 */
public class AwsSample {

    public static void main(String[] args){

        AmazonEC2Client amazonEC2Client = new AmazonEC2Client();
        String keyName = "my-aws-ec2-keypair";
        String groupName = "my-security-group";

        CreateSecurityGroupRequest csgr = new CreateSecurityGroupRequest();
        csgr.withGroupName(groupName).withDescription("My security group");
        CreateSecurityGroupResult createSecurityGroupResult = amazonEC2Client.createSecurityGroup(csgr);
        IpPermission ipPermission = new IpPermission();

        ipPermission.withIpRanges("111.111.111.111/32", "150.150.150.150/32")
                .withIpProtocol("tcp")
                .withFromPort(22)
                .withToPort(22);

        AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest = new AuthorizeSecurityGroupIngressRequest();

        authorizeSecurityGroupIngressRequest.withGroupName(groupName).withIpPermissions(ipPermission);

        amazonEC2Client.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);



        CreateKeyPairRequest createKeyPairRequest = new CreateKeyPairRequest();

        createKeyPairRequest.withKeyName(keyName);

        CreateKeyPairResult createKeyPairResult = amazonEC2Client.createKeyPair(createKeyPairRequest);
        KeyPair keyPair = new KeyPair();

        keyPair = createKeyPairResult.getKeyPair();

        String privateKey = keyPair.getKeyMaterial();

        System.out.println(privateKey);

        RunInstancesRequest runInstancesRequest =
                new RunInstancesRequest();

        runInstancesRequest.withImageId("ami-0396cd69")
                .withInstanceType("t2.small")
                .withMinCount(1)
                .withMaxCount(1)
                .withKeyName(keyName)
                .withSecurityGroups(groupName);

        RunInstancesResult result = amazonEC2Client.runInstances(runInstancesRequest);

        System.out.println(result.toString());
    }

}

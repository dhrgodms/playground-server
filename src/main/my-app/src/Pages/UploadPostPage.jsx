import React, {useCallback, useRef, useState} from 'react';
import {
    FormControl,
    FormLabel,
    Input,
    InputGroup,
    InputRightElement,
    Button, useToast, Flex, Box, Heading, Textarea, TabPanel, Tab, TabPanels, TabList, Tabs,
} from '@chakra-ui/react';
import SubTemplate from "../Templates/SubTemplate";
import {WriteForm} from "../Articles/UploadForm/WriteForm";
function UploadForm() {

        return (
            <SubTemplate titleQuery={"uploadform"} pageTitle={"uploadform"}>
                <Tabs variant='soft-rounded' colorScheme='yellow'>
                    <TabList>
                        <Tab>글</Tab>
                        <Tab>그림</Tab>
                        <Tab>플레이리스트</Tab>
                    </TabList>
                    <TabPanels>
                        <TabPanel>
                            <WriteForm />
                        </TabPanel>
                        <TabPanel>
                            <p>그림을 업로드하는 폼~</p>
                        </TabPanel>
                        <TabPanel>
                            <p>플리 업로드하는 폼~</p>
                        </TabPanel>
                    </TabPanels>
                </Tabs>
            </SubTemplate>
        );
}

const UploadPostPage = () => {
    return(<Flex justify={'center'}>
        <Box>
            <UploadForm />
        </Box>
    </Flex>);
}
export default UploadPostPage;
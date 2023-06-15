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
import {ImageForm} from "../Articles/UploadForm/ImageForm";
import MarkdownForm from "../Articles/UploadForm/MarkdownForm";
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
                            <WriteForm tag={1}/>
                        </TabPanel>
                        <TabPanel>
                            <ImageForm tag={2} />
                        </TabPanel>
                        <TabPanel>
                            <WriteForm tag={3}/>
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